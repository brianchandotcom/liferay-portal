/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.storage.helper;

import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.DLStore;
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.lock.DuplicateLockException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.site.storage.helper.SitemapStorageHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = SitemapStorageHelper.class)
public class SitemapStorageHelperImpl implements SitemapStorageHelper {

	@Override
	public void deleteSitemap(
			long companyId, long groupId, String childSitemapKey, int page)
		throws PortalException {

		_deleteFile(
			companyId, _getSitemapFileName(groupId, childSitemapKey, page));
	}

	@Override
	public void deleteSitemaps(long companyId) throws PortalException {
		_deleteFiles(companyId, _getDirName());

		try {
			_storeSizes();
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}

		String lastRegenerateSitemapDateFileName =
			_getLastRegenerateSitemapDateFileName();

		if (_dlStore.hasFile(
				companyId, CompanyConstants.SYSTEM,
				lastRegenerateSitemapDateFileName, Store.VERSION_DEFAULT)) {

			_dlStore.deleteFile(
				companyId, CompanyConstants.SYSTEM,
				lastRegenerateSitemapDateFileName);
		}
	}

	@Override
	public void deleteSitemaps(long companyId, long groupId)
		throws PortalException {

		_deleteFiles(companyId, _getDirName(groupId));
	}

	@Override
	public void deleteSitemaps(
			long companyId, long groupId, String childSitemapKey)
		throws PortalException {

		_deleteFiles(companyId, _getDirName(groupId, childSitemapKey));
	}

	@Override
	public Date getLastRegenerateSitemapDate(long companyId)
		throws PortalException {

		String lastRegenerateSitemapDateFileName =
			_getLastRegenerateSitemapDateFileName();

		if (!_dlStore.hasFile(
				companyId, CompanyConstants.SYSTEM,
				lastRegenerateSitemapDateFileName, Store.VERSION_DEFAULT)) {

			return null;
		}

		try (InputStream inputStream = _dlStore.getFileAsStream(
				companyId, CompanyConstants.SYSTEM,
				lastRegenerateSitemapDateFileName, Store.VERSION_DEFAULT)) {

			return new Date(GetterUtil.getLong(StringUtil.read(inputStream)));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Override
	public Map<String, Integer> getPageCountsByChildSitemapKey(
			long companyId, long groupId)
		throws PortalException {

		Map<String, Integer> pageCountsByChildSitemapKey = new HashMap<>();

		String dirName = _getDirName(groupId);

		for (String fileName : _getFileNames(companyId, dirName)) {
			int index = fileName.lastIndexOf(CharPool.SLASH);

			if (index <= dirName.length()) {
				continue;
			}

			pageCountsByChildSitemapKey.merge(
				fileName.substring(dirName.length() + 1, index), 1,
				Integer::sum);
		}

		return pageCountsByChildSitemapKey;
	}

	@Override
	public InputStream getSitemapInputStream(long companyId, long groupId)
		throws PortalException {

		return _getInputStream(companyId, _getSitemapFileName(groupId));
	}

	@Override
	public InputStream getSitemapInputStream(
			long companyId, long groupId, String childSitemapKey, int page)
		throws PortalException {

		return _getInputStream(
			companyId, _getSitemapFileName(groupId, childSitemapKey, page));
	}

	@Override
	public boolean hasSitemapFile(long companyId, long groupId)
		throws PortalException {

		return _hasFile(companyId, _getSitemapFileName(groupId));
	}

	@Override
	public boolean hasSitemapFile(
			long companyId, long groupId, String childSitemapKey, int page)
		throws PortalException {

		return _hasFile(
			companyId, _getSitemapFileName(groupId, childSitemapKey, page));
	}

	@Override
	public boolean hasSitemapFiles(long companyId) throws PortalException {
		return ArrayUtil.isNotEmpty(_getFileNames(companyId, _getDirName()));
	}

	@Override
	public boolean isPortalCacheStorage() throws PortalException {
		try {
			ObjectValuePair<Boolean, Long> objectValuePair =
				_getStorageMetadataObjectValuePair();

			return objectValuePair.getKey();
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Override
	public void moveSitemapFilesToPortalCache() throws PortalException {
		if (isPortalCacheStorage() || !_lockStorageMetadata()) {
			return;
		}

		try {
			JSONObject storageMetadataJSONObject =
				_getStorageMetadataJSONObject();

			if (storageMetadataJSONObject.getBoolean("portalCacheStorage")) {
				return;
			}

			Map<Long, Long> sizesMap = _getSizesMap(storageMetadataJSONObject);

			_applySizeDeltas(sizesMap);

			if (_getTotalSize(sizesMap) >= _maxTotalSize) {
				if (!sizesMap.equals(_getSizesMap(storageMetadataJSONObject))) {
					_storeStorageMetadataFile(false, sizesMap);
				}

				return;
			}

			Map<Long, Long> dlStoreSizesMap = new HashMap<>();

			_companyLocalService.forEachCompanyId(
				companyId -> {
					long companySize = 0;

					for (String fileName :
							_dlStore.getFileNames(
								companyId, CompanyConstants.SYSTEM,
								_getDirName())) {

						companySize += _dlStore.getFileSize(
							companyId, CompanyConstants.SYSTEM, fileName);
					}

					if (companySize > 0) {
						dlStoreSizesMap.put(companyId, companySize);
					}
				});

			if (_getTotalSize(dlStoreSizesMap) >= _maxTotalSize) {
				_storeStorageMetadataFile(false, dlStoreSizesMap);

				return;
			}

			_storeStorageMetadataFile(true, dlStoreSizesMap);

			_companyLocalService.forEachCompanyId(
				this::_moveDLStoreFilesToPortalCache);

			_sizeDeltasMap.clear();

			_storeStorageMetadataFile(true, _getPortalCacheSizesMap());
		}
		catch (Exception exception) {
			if (exception instanceof PortalException) {
				throw (PortalException)exception;
			}

			throw new PortalException(exception);
		}
		finally {
			_unlockStorageMetadata();
		}
	}

	@Override
	public void storeLastRegenerateSitemapDateFile(long companyId)
		throws PortalException {

		String lastRegenerateSitemapDateFileName =
			_getLastRegenerateSitemapDateFileName();

		if (_dlStore.hasFile(
				companyId, CompanyConstants.SYSTEM,
				lastRegenerateSitemapDateFileName, Store.VERSION_DEFAULT)) {

			_dlStore.deleteFile(
				companyId, CompanyConstants.SYSTEM,
				lastRegenerateSitemapDateFileName);
		}

		String lastRegenerateSitemapTime = String.valueOf(
			System.currentTimeMillis());

		_addDLStoreFile(
			lastRegenerateSitemapTime.getBytes(StandardCharsets.UTF_8),
			companyId, lastRegenerateSitemapDateFileName);

		try {
			_storeSizes();
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Override
	public void storeSitemapFile(long companyId, long groupId, String xml)
		throws PortalException {

		try {
			_storeFile(
				xml.getBytes(StandardCharsets.UTF_8), companyId,
				_getSitemapFileName(groupId));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Override
	public void storeSitemapFile(
			long companyId, long groupId, String childSitemapKey, int page,
			String xml)
		throws PortalException {

		try {
			_storeFile(
				xml.getBytes(StandardCharsets.UTF_8), companyId,
				_getSitemapFileName(groupId, childSitemapKey, page));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Activate
	protected void activate() {
		_maxTotalSize = 20 * 1024 * 1024;

		_portalCache =
			(PortalCache<String, Serializable>)_multiVMPool.getPortalCache(
				SitemapStorageHelperImpl.class.getName(), false, false);
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(
			SitemapStorageHelperImpl.class.getName());
	}

	private void _addDLStoreFile(byte[] bytes, long companyId, String fileName)
		throws PortalException {

		DLStoreRequest dlStoreRequest = DLStoreRequest.builder(
			companyId, CompanyConstants.SYSTEM, fileName
		).size(
			bytes.length
		).versionLabel(
			Store.VERSION_DEFAULT
		).build();

		_dlStore.addFile(dlStoreRequest, bytes);
	}

	private void _addSizeDelta(long companyId, long sizeDelta) {
		LongAdder longAdder = _sizeDeltasMap.computeIfAbsent(
			companyId, curCompanyId -> new LongAdder());

		longAdder.add(sizeDelta);
	}

	private void _applySizeDeltas(Map<Long, Long> sizesMap) {
		for (Map.Entry<Long, LongAdder> entry : _sizeDeltasMap.entrySet()) {
			LongAdder longAdder = entry.getValue();

			long size =
				GetterUtil.getLong(sizesMap.get(entry.getKey())) +
					longAdder.sumThenReset();

			if (size > 0) {
				sizesMap.put(entry.getKey(), size);
			}
			else {
				sizesMap.remove(entry.getKey());
			}
		}
	}

	private void _deleteDLStoreFiles(long companyId, String[] fileNames) {
		for (String fileName : fileNames) {
			try {
				_dlStore.deleteFile(
					companyId, CompanyConstants.SYSTEM, fileName);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}
	}

	private void _deleteFile(long companyId, String fileName)
		throws PortalException {

		if (isPortalCacheStorage()) {
			String portalCacheKey = _getPortalCacheKey(companyId, fileName);

			_addSizeDelta(companyId, -_getPortalCacheFileSize(portalCacheKey));

			_portalCache.remove(portalCacheKey);

			return;
		}

		_addSizeDelta(companyId, -_getDLStoreFileSize(companyId, fileName));

		_dlStore.deleteFile(companyId, CompanyConstants.SYSTEM, fileName);
	}

	private void _deleteFiles(long companyId, String dirName)
		throws PortalException {

		for (String fileName : _getFileNames(companyId, dirName)) {
			try {
				_deleteFile(companyId, fileName);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}
	}

	private long _getCurrentTotalSize() throws IOException, PortalException {
		ObjectValuePair<Boolean, Long> objectValuePair =
			_getStorageMetadataObjectValuePair();

		long totalSize = objectValuePair.getValue();

		for (LongAdder longAdder : _sizeDeltasMap.values()) {
			totalSize += longAdder.sum();
		}

		return totalSize;
	}

	private byte[] _getDLStoreFileBytes(long companyId, String fileName)
		throws IOException, PortalException {

		try (InputStream inputStream = _dlStore.getFileAsStream(
				companyId, CompanyConstants.SYSTEM, fileName,
				Store.VERSION_DEFAULT)) {

			return FileUtil.getBytes(inputStream);
		}
	}

	private long _getDLStoreFileSize(long companyId, String fileName)
		throws PortalException {

		if (!_dlStore.hasFile(
				companyId, CompanyConstants.SYSTEM, fileName,
				Store.VERSION_DEFAULT)) {

			return 0;
		}

		return _dlStore.getFileSize(
			companyId, CompanyConstants.SYSTEM, fileName);
	}

	private String _getDirName() {
		return "sitemaps";
	}

	private String _getDirName(long groupId) {
		return "sitemaps/" + groupId;
	}

	private String _getDirName(long groupId, String childSitemapKey) {
		return StringBundler.concat(
			"sitemaps/", groupId, StringPool.SLASH, childSitemapKey);
	}

	private String[] _getFileNames(long companyId, String dirName)
		throws PortalException {

		if (!isPortalCacheStorage()) {
			return _dlStore.getFileNames(
				companyId, CompanyConstants.SYSTEM, dirName);
		}

		String portalCacheKeyPrefix =
			_getPortalCacheKey(companyId, dirName) + StringPool.SLASH;

		return TransformUtil.transformToArray(
			_portalCache.getKeys(),
			portalCacheKey -> {
				if (!portalCacheKey.startsWith(portalCacheKeyPrefix)) {
					return null;
				}

				return portalCacheKey.substring(
					portalCacheKey.indexOf(CharPool.SLASH) + 1);
			},
			String.class);
	}

	private InputStream _getInputStream(long companyId, String fileName)
		throws PortalException {

		if (!isPortalCacheStorage()) {
			return _dlStore.getFileAsStream(
				companyId, CompanyConstants.SYSTEM, fileName,
				Store.VERSION_DEFAULT);
		}

		byte[] bytes = _getPortalCacheBytes(
			_getPortalCacheKey(companyId, fileName));

		if (bytes == null) {
			throw new NoSuchFileException(
				companyId, CompanyConstants.SYSTEM, fileName);
		}

		return new ByteArrayInputStream(bytes);
	}

	private String _getLastRegenerateSitemapDateFileName() {
		return "sitemap-metadata/last-regenerate-sitemap-date";
	}

	private byte[] _getPortalCacheBytes(String portalCacheKey) {
		return (byte[])_portalCache.get(portalCacheKey);
	}

	private int _getPortalCacheFileSize(String portalCacheKey) {
		byte[] bytes = _getPortalCacheBytes(portalCacheKey);

		if (bytes == null) {
			return 0;
		}

		return bytes.length;
	}

	private String _getPortalCacheKey(long companyId, String fileName) {
		return StringBundler.concat(companyId, StringPool.SLASH, fileName);
	}

	private Map<Long, Long> _getPortalCacheSizesMap() {
		Map<Long, Long> portalCacheSizesMap = new HashMap<>();

		for (String portalCacheKey : _portalCache.getKeys()) {
			if (StringUtil.equals(
					portalCacheKey, _PORTAL_CACHE_KEY_STORAGE_METADATA)) {

				continue;
			}

			byte[] bytes = _getPortalCacheBytes(portalCacheKey);

			if (bytes == null) {
				continue;
			}

			portalCacheSizesMap.merge(
				GetterUtil.getLong(
					portalCacheKey.substring(
						0, portalCacheKey.indexOf(CharPool.SLASH))),
				(long)bytes.length, Long::sum);
		}

		return portalCacheSizesMap;
	}

	private String _getSitemapFileName(long groupId) {
		return StringBundler.concat("sitemaps/", groupId, "/sitemap-index.xml");
	}

	private String _getSitemapFileName(
		long groupId, String childSitemapKey, int page) {

		return StringBundler.concat(
			"sitemaps/", groupId, StringPool.SLASH, childSitemapKey,
			StringPool.SLASH, page, ".xml");
	}

	private Map<Long, Long> _getSizesMap(JSONObject storageMetadataJSONObject) {
		Map<Long, Long> sizesMap = new HashMap<>();

		JSONObject sizesJSONObject = storageMetadataJSONObject.getJSONObject(
			"sizes");

		if (sizesJSONObject != null) {
			for (String key : sizesJSONObject.keySet()) {
				sizesMap.put(
					GetterUtil.getLong(key), sizesJSONObject.getLong(key));
			}
		}

		return sizesMap;
	}

	private String _getStorageMetadataFileName() {
		return "sitemap-metadata/storage";
	}

	private JSONObject _getStorageMetadataJSONObject()
		throws IOException, PortalException {

		long companyId = _portal.getDefaultCompanyId();
		String fileName = _getStorageMetadataFileName();

		if (!_dlStore.hasFile(
				companyId, CompanyConstants.SYSTEM, fileName,
				Store.VERSION_DEFAULT)) {

			return _jsonFactory.createJSONObject();
		}

		return _jsonFactory.createJSONObject(
			new String(
				_getDLStoreFileBytes(companyId, fileName),
				StandardCharsets.UTF_8));
	}

	private ObjectValuePair<Boolean, Long> _getStorageMetadataObjectValuePair()
		throws IOException, PortalException {

		ObjectValuePair<Boolean, Long> objectValuePair =
			(ObjectValuePair<Boolean, Long>)_portalCache.get(
				_PORTAL_CACHE_KEY_STORAGE_METADATA);

		if (objectValuePair != null) {
			return objectValuePair;
		}

		JSONObject storageMetadataJSONObject = _getStorageMetadataJSONObject();

		objectValuePair = new ObjectValuePair<>(
			storageMetadataJSONObject.getBoolean("portalCacheStorage"),
			_getTotalSize(_getSizesMap(storageMetadataJSONObject)));

		PortalCacheHelperUtil.putWithoutReplicator(
			_portalCache, _PORTAL_CACHE_KEY_STORAGE_METADATA, objectValuePair);

		return objectValuePair;
	}

	private long _getTotalSize(Map<Long, Long> sizesMap) {
		long totalSize = 0;

		for (long companySize : sizesMap.values()) {
			totalSize += companySize;
		}

		return totalSize;
	}

	private boolean _hasFile(long companyId, String fileName)
		throws PortalException {

		if (!isPortalCacheStorage()) {
			return _dlStore.hasFile(
				companyId, CompanyConstants.SYSTEM, fileName,
				Store.VERSION_DEFAULT);
		}

		if (_portalCache.get(_getPortalCacheKey(companyId, fileName)) != null) {
			return true;
		}

		return false;
	}

	private boolean _hasSizeDeltas() {
		for (LongAdder longAdder : _sizeDeltasMap.values()) {
			if (longAdder.sum() != 0) {
				return true;
			}
		}

		return false;
	}

	private boolean _lockStorageMetadata() throws PortalException {
		_reentrantLock.lock();

		boolean locked = false;

		try {
			Lock lock = _lockManager.lock(
				_userLocalService.getGuestUserId(_portal.getDefaultCompanyId()),
				SitemapStorageHelperImpl.class.getName(),
				_getStorageMetadataFileName(),
				SitemapStorageHelperImpl.class.getName(), false,
				Time.MINUTE * 20, false);

			locked = lock.isNew();
		}
		catch (DuplicateLockException duplicateLockException) {
			if (_log.isDebugEnabled()) {
				_log.debug(duplicateLockException);
			}
		}
		catch (RuntimeException runtimeException) {
			if (_log.isWarnEnabled()) {
				_log.warn(runtimeException);
			}
		}
		finally {
			if (!locked) {
				_reentrantLock.unlock();
			}
		}

		return locked;
	}

	private void _moveDLStoreFilesToPortalCache(long companyId)
		throws IOException, PortalException {

		String[] fileNames = _dlStore.getFileNames(
			companyId, CompanyConstants.SYSTEM, _getDirName());

		for (String fileName : fileNames) {
			String portalCacheKey = _getPortalCacheKey(companyId, fileName);

			if (_portalCache.get(portalCacheKey) != null) {
				continue;
			}

			_portalCache.put(
				portalCacheKey, _getDLStoreFileBytes(companyId, fileName));
		}

		_deleteDLStoreFiles(companyId, fileNames);
	}

	private boolean _moveSitemapFilesToDLStore()
		throws IOException, PortalException {

		if (!_lockStorageMetadata()) {
			return false;
		}

		try {
			JSONObject storageMetadataJSONObject =
				_getStorageMetadataJSONObject();

			if (!storageMetadataJSONObject.getBoolean("portalCacheStorage")) {
				return true;
			}

			for (String portalCacheKey : _portalCache.getKeys()) {
				if (StringUtil.equals(
						portalCacheKey, _PORTAL_CACHE_KEY_STORAGE_METADATA)) {

					continue;
				}

				byte[] bytes = _getPortalCacheBytes(portalCacheKey);

				if (bytes == null) {
					continue;
				}

				int index = portalCacheKey.indexOf(CharPool.SLASH);

				_addDLStoreFile(
					bytes,
					GetterUtil.getLong(portalCacheKey.substring(0, index)),
					portalCacheKey.substring(index + 1));
			}

			Map<Long, Long> sizesMap = _getPortalCacheSizesMap();

			_sizeDeltasMap.clear();

			_portalCache.removeAll();

			_storeStorageMetadataFile(false, sizesMap);

			return true;
		}
		finally {
			_unlockStorageMetadata();
		}
	}

	private long _recountTotalSize() throws IOException, PortalException {
		if (!_lockStorageMetadata()) {
			return _getCurrentTotalSize();
		}

		try {
			Map<Long, Long> sizesMap = _getPortalCacheSizesMap();

			_sizeDeltasMap.clear();

			_storeStorageMetadataFile(true, sizesMap);

			return _getTotalSize(sizesMap);
		}
		finally {
			_unlockStorageMetadata();
		}
	}

	private void _storeFile(byte[] bytes, long companyId, String fileName)
		throws IOException, PortalException {

		if (isPortalCacheStorage()) {
			synchronized (this) {
				if (isPortalCacheStorage()) {
					String portalCacheKey = _getPortalCacheKey(
						companyId, fileName);

					long sizeDelta =
						bytes.length - _getPortalCacheFileSize(portalCacheKey);

					if (((_getCurrentTotalSize() + sizeDelta) <
							_maxTotalSize) ||
						((_recountTotalSize() + sizeDelta) < _maxTotalSize) ||
						!_moveSitemapFilesToDLStore()) {

						_addSizeDelta(companyId, sizeDelta);

						_portalCache.put(portalCacheKey, bytes);

						return;
					}
				}
			}
		}

		_addSizeDelta(
			companyId, bytes.length - _getDLStoreFileSize(companyId, fileName));

		_addDLStoreFile(bytes, companyId, fileName);
	}

	private void _storeSizes() throws IOException, PortalException {
		if (!_hasSizeDeltas() || !_lockStorageMetadata()) {
			return;
		}

		try {
			JSONObject storageMetadataJSONObject =
				_getStorageMetadataJSONObject();

			Map<Long, Long> sizesMap = _getSizesMap(storageMetadataJSONObject);

			_applySizeDeltas(sizesMap);

			_storeStorageMetadataFile(
				storageMetadataJSONObject.getBoolean("portalCacheStorage"),
				sizesMap);
		}
		finally {
			_unlockStorageMetadata();
		}
	}

	private void _storeStorageMetadataFile(
			boolean portalCacheStorage, Map<Long, Long> sizesMap)
		throws PortalException {

		long companyId = _portal.getDefaultCompanyId();
		String fileName = _getStorageMetadataFileName();

		if (_dlStore.hasFile(
				companyId, CompanyConstants.SYSTEM, fileName,
				Store.VERSION_DEFAULT)) {

			_dlStore.deleteFile(companyId, CompanyConstants.SYSTEM, fileName);
		}

		String json = JSONUtil.put(
			"portalCacheStorage", portalCacheStorage
		).put(
			"sizes", _jsonFactory.createJSONObject(sizesMap)
		).toString();

		_addDLStoreFile(
			json.getBytes(StandardCharsets.UTF_8), companyId, fileName);

		_portalCache.put(
			_PORTAL_CACHE_KEY_STORAGE_METADATA,
			new ObjectValuePair<>(portalCacheStorage, _getTotalSize(sizesMap)));
	}

	private void _unlockStorageMetadata() {
		try {
			_lockManager.unlock(
				SitemapStorageHelperImpl.class.getName(),
				_getStorageMetadataFileName());
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	private static final String _PORTAL_CACHE_KEY_STORAGE_METADATA =
		"storage-metadata";

	private static final Log _log = LogFactoryUtil.getLog(
		SitemapStorageHelperImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLStore _dlStore;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LockManager _lockManager;

	private long _maxTotalSize;

	@Reference
	private MultiVMPool _multiVMPool;

	@Reference
	private Portal _portal;

	private PortalCache<String, Serializable> _portalCache;
	private final ReentrantLock _reentrantLock = new ReentrantLock();
	private final Map<Long, LongAdder> _sizeDeltasMap =
		new ConcurrentHashMap<>();

	@Reference
	private UserLocalService _userLocalService;

}