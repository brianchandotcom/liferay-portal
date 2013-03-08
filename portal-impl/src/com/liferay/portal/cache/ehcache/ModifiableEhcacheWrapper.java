/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cache.ehcache;

import java.beans.PropertyChangeListener;

import java.io.Serializable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;
import net.sf.ehcache.Status;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.event.RegisteredEventListeners;
import net.sf.ehcache.exceptionhandler.CacheExceptionHandler;
import net.sf.ehcache.extension.CacheExtension;
import net.sf.ehcache.loader.CacheLoader;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.statistics.CacheUsageListener;
import net.sf.ehcache.statistics.LiveCacheStatistics;
import net.sf.ehcache.statistics.sampled.SampledCacheStatistics;
import net.sf.ehcache.terracotta.TerracottaNotRunningException;
import net.sf.ehcache.transaction.manager.TransactionManagerLookup;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.CacheWriterManager;

/**
 * @author Edward Han
 */
public class ModifiableEhcacheWrapper implements Ehcache {

	public ModifiableEhcacheWrapper(Ehcache ehcache) {
		_ehcache = ehcache;
	}

	public void acquireReadLockOnKey(Object key) {
		_ehcache.acquireReadLockOnKey(key);
	}

	public void acquireWriteLockOnKey(Object key) {
		_ehcache.acquireWriteLockOnKey(key);
	}

	public void addPropertyChangeListener(
		PropertyChangeListener propertyChangeListener) {

		_ehcache.addPropertyChangeListener(propertyChangeListener);
	}

	public void addReference() {
		_referenceCounter.incrementAndGet();
	}

	public void bootstrap() {
		_ehcache.bootstrap();
	}

	public long calculateInMemorySize()
		throws CacheException, IllegalStateException {

		return _ehcache.calculateInMemorySize();
	}

	public long calculateOffHeapSize()
		throws CacheException, IllegalStateException {

		return _ehcache.calculateOffHeapSize();
	}

	public void clearStatistics() {
		_ehcache.clearStatistics();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return _ehcache.clone();
	}

	public Query createQuery() {
		return _ehcache.createQuery();
	}

	public void disableDynamicFeatures() {
		_ehcache.disableDynamicFeatures();
	}

	public void dispose() throws IllegalStateException {
		_ehcache.dispose();
	}

	@Override
	public boolean equals(Object object) {
		return _ehcache.equals(object);
	}

	public void evictExpiredElements() {
		_ehcache.evictExpiredElements();
	}

	public void flush() throws CacheException, IllegalStateException {
		_ehcache.flush();
	}

	public Element get(Object key)
		throws CacheException, IllegalStateException {

		return _ehcache.get(key);
	}

	public Element get(Serializable key)
		throws CacheException, IllegalStateException {

		return _ehcache.get(key);
	}

	public int getActiveReferenceCount() {
		return _referenceCounter.get();
	}

	@SuppressWarnings("rawtypes")
	public Map getAllWithLoader(Collection keys, Object argument)
		throws CacheException {

		return _ehcache.getAllWithLoader(keys, argument);
	}

	public float getAverageGetTime() {
		return _ehcache.getAverageGetTime();
	}

	public long getAverageSearchTime() {
		return _ehcache.getAverageSearchTime();
	}

	public BootstrapCacheLoader getBootstrapCacheLoader() {
		return _ehcache.getBootstrapCacheLoader();
	}

	public CacheConfiguration getCacheConfiguration() {
		return _ehcache.getCacheConfiguration();
	}

	public RegisteredEventListeners getCacheEventNotificationService() {
		return _ehcache.getCacheEventNotificationService();
	}

	public CacheExceptionHandler getCacheExceptionHandler() {
		return _ehcache.getCacheExceptionHandler();
	}

	public CacheManager getCacheManager() {
		return _ehcache.getCacheManager();
	}

	public int getDiskStoreSize() throws IllegalStateException {
		return _ehcache.getDiskStoreSize();
	}

	public String getGuid() {
		return _ehcache.getGuid();
	}

	public Object getInternalContext() {
		return _ehcache.getInternalContext();
	}

	@SuppressWarnings("rawtypes")
	public List getKeys() throws CacheException, IllegalStateException {
		return _ehcache.getKeys();
	}

	@SuppressWarnings("rawtypes")
	public List getKeysNoDuplicateCheck() throws IllegalStateException {
		return _ehcache.getKeysNoDuplicateCheck();
	}

	@SuppressWarnings("rawtypes")
	public List getKeysWithExpiryCheck()
		throws CacheException, IllegalStateException {

		return _ehcache.getKeysWithExpiryCheck();
	}

	public LiveCacheStatistics getLiveCacheStatistics()
		throws IllegalStateException {

		return _ehcache.getLiveCacheStatistics();
	}

	public long getMemoryStoreSize() throws IllegalStateException {
		return _ehcache.getMemoryStoreSize();
	}

	public String getName() {
		return _ehcache.getName();
	}

	public long getOffHeapStoreSize() throws IllegalStateException {
		return _ehcache.getOffHeapStoreSize();
	}

	public Element getQuiet(Object key)
		throws CacheException, IllegalStateException {

		return _ehcache.getQuiet(key);
	}

	public Element getQuiet(Serializable key)
		throws CacheException, IllegalStateException {

		return _ehcache.getQuiet(key);
	}

	public List<CacheExtension> getRegisteredCacheExtensions() {
		return _ehcache.getRegisteredCacheExtensions();
	}

	public List<CacheLoader> getRegisteredCacheLoaders() {
		return _ehcache.getRegisteredCacheLoaders();
	}

	public CacheWriter getRegisteredCacheWriter() {
		return _ehcache.getRegisteredCacheWriter();
	}

	public SampledCacheStatistics getSampledCacheStatistics() {
		return _ehcache.getSampledCacheStatistics();
	}

	public <T> Attribute<T> getSearchAttribute(String attributeName)
		throws CacheException {

		return _ehcache.getSearchAttribute(attributeName);
	}

	public long getSearchesPerSecond() {
		return _ehcache.getSearchesPerSecond();
	}

	public int getSize() throws CacheException, IllegalStateException {
		return _ehcache.getSize();
	}

	public int getSizeBasedOnAccuracy(int statisticsAccuracy)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		return _ehcache.getSizeBasedOnAccuracy(statisticsAccuracy);
	}

	public Statistics getStatistics() throws IllegalStateException {
		return _ehcache.getStatistics();
	}

	public int getStatisticsAccuracy() {
		return _ehcache.getStatisticsAccuracy();
	}

	public Status getStatus() {
		return _ehcache.getStatus();
	}

	public Element getWithLoader(
			Object key, CacheLoader cacheLoader, Object argument)
		throws CacheException {

		return _ehcache.getWithLoader(key, cacheLoader, argument);
	}

	public Ehcache getWrappedCache() {
		return _ehcache;
	}

	public CacheWriterManager getWriterManager() {
		return _ehcache.getWriterManager();
	}

	@Override
	public int hashCode() {
		return _ehcache.hashCode();
	}

	public void initialise() {
		_ehcache.initialise();
	}

	public boolean isClusterBulkLoadEnabled()
		throws TerracottaNotRunningException, UnsupportedOperationException {

		return _ehcache.isClusterBulkLoadEnabled();
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public boolean isClusterCoherent() {
		return _ehcache.isClusterCoherent();
	}

	public boolean isDisabled() {
		return _ehcache.isDisabled();
	}

	public boolean isElementInMemory(Object key) {
		return _ehcache.isElementInMemory(key);
	}

	public boolean isElementInMemory(Serializable key) {
		return _ehcache.isElementInMemory(key);
	}

	public boolean isElementOnDisk(Object key) {
		return _ehcache.isElementOnDisk(key);
	}

	public boolean isElementOnDisk(Serializable key) {
		return _ehcache.isElementOnDisk(key);
	}

	public boolean isExpired(Element element)
		throws IllegalStateException, NullPointerException {

		return _ehcache.isExpired(element);
	}

	public boolean isKeyInCache(Object key) {
		return _ehcache.isKeyInCache(key);
	}

	public boolean isNodeBulkLoadEnabled()
		throws TerracottaNotRunningException, UnsupportedOperationException {

		return _ehcache.isNodeBulkLoadEnabled();
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public boolean isNodeCoherent() {
		return _ehcache.isNodeCoherent();
	}

	public boolean isReadLockedByCurrentThread(Object key) {
		return _ehcache.isReadLockedByCurrentThread(key);
	}

	public boolean isSampledStatisticsEnabled() {
		return _ehcache.isSampledStatisticsEnabled();
	}

	public boolean isSearchable() {
		return _ehcache.isSearchable();
	}

	public boolean isStatisticsEnabled() {
		return _ehcache.isStatisticsEnabled();
	}

	public boolean isValueInCache(Object value) {
		return _ehcache.isValueInCache(value);
	}

	public boolean isWriteLockedByCurrentThread(Object key) {
		return _ehcache.isWriteLockedByCurrentThread(key);
	}

	public void load(Object key) throws CacheException {
		_ehcache.load(key);
	}

	@SuppressWarnings("rawtypes")
	public void loadAll(Collection keys, Object argument)
		throws CacheException {

		_ehcache.loadAll(keys, argument);
	}

	public void put(Element element)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		_ehcache.put(element);
	}

	public void put(Element element, boolean doNotNotifyCacheReplicators)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		_ehcache.put(element, doNotNotifyCacheReplicators);
	}

	public Element putIfAbsent(Element element) throws NullPointerException {
		return _ehcache.putIfAbsent(element);
	}

	public void putQuiet(Element element)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		_ehcache.putQuiet(element);
	}

	public void putWithWriter(Element element)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		_ehcache.putWithWriter(element);
	}

	public void registerCacheExtension(CacheExtension cacheExtension) {
		_ehcache.registerCacheExtension(cacheExtension);
	}

	public void registerCacheLoader(CacheLoader cacheLoader) {
		_ehcache.registerCacheLoader(cacheLoader);
	}

	public void registerCacheUsageListener(
			CacheUsageListener cacheUsageListener)
		throws IllegalStateException {

		_ehcache.registerCacheUsageListener(cacheUsageListener);
	}

	public void registerCacheWriter(CacheWriter cacheWriter) {
		_ehcache.registerCacheWriter(cacheWriter);
	}

	public void releaseReadLockOnKey(Object key) {
		_ehcache.releaseReadLockOnKey(key);
	}

	public void releaseWriteLockOnKey(Object key) {
		_ehcache.releaseWriteLockOnKey(key);
	}

	public boolean remove(Object key) throws IllegalStateException {
		return _ehcache.remove(key);
	}

	public boolean remove(Object key, boolean doNotNotifyCacheReplicators)
		throws IllegalStateException {

		return _ehcache.remove(key, doNotNotifyCacheReplicators);
	}

	public boolean remove(Serializable key) throws IllegalStateException {
		return _ehcache.remove(key);
	}

	public boolean remove(Serializable key, boolean doNotNotifyCacheReplicators)
		throws IllegalStateException {

		return _ehcache.remove(key, doNotNotifyCacheReplicators);
	}

	public void removeAll() throws CacheException, IllegalStateException {
		if (!isStatusAlive()) {
			return;
		}

		_ehcache.removeAll();
	}

	public void removeAll(boolean doNotNotifyCacheReplicators)
		throws CacheException, IllegalStateException {

		if (!isStatusAlive()) {
			return;
		}

		_ehcache.removeAll(doNotNotifyCacheReplicators);
	}

	public void removeCacheUsageListener(CacheUsageListener cacheUsageListener)
		throws IllegalStateException {

		_ehcache.removeCacheUsageListener(cacheUsageListener);
	}

	public boolean removeElement(Element element) throws NullPointerException {
		if (!isStatusAlive()) {
			return true;
		}

		return _ehcache.removeElement(element);
	}

	public void removePropertyChangeListener(
		PropertyChangeListener propertyChangeListener) {

		_ehcache.removePropertyChangeListener(propertyChangeListener);
	}

	public boolean removeQuiet(Object key) throws IllegalStateException {
		if (!isStatusAlive()) {
			return true;
		}

		return _ehcache.removeQuiet(key);
	}

	public boolean removeQuiet(Serializable key) throws IllegalStateException {
		if (!isStatusAlive()) {
			return true;
		}

		return _ehcache.removeQuiet(key);
	}

	public void removeReference() {
		_referenceCounter.decrementAndGet();
	}

	public boolean removeWithWriter(Object key)
		throws CacheException, IllegalStateException {

		if (!isStatusAlive()) {
			return true;
		}

		return _ehcache.removeWithWriter(key);
	}

	public Element replace(Element element) throws NullPointerException {
		return _ehcache.replace(element);
	}

	public boolean replace(Element oldElement, Element newElement)
		throws IllegalArgumentException, NullPointerException {

		return _ehcache.replace(oldElement, newElement);
	}

	public void setBootstrapCacheLoader(
			BootstrapCacheLoader bootstrapCacheLoader)
		throws CacheException {

		_ehcache.setBootstrapCacheLoader(bootstrapCacheLoader);
	}

	public void setCacheExceptionHandler(
		CacheExceptionHandler cacheExceptionHandler) {

		_ehcache.setCacheExceptionHandler(cacheExceptionHandler);
	}

	public void setCacheManager(CacheManager cacheManager) {
		_ehcache.setCacheManager(cacheManager);
	}

	public void setDisabled(boolean disabled) {
		_ehcache.setDisabled(disabled);
	}

	public void setDiskStorePath(String diskStorePath) throws CacheException {
		_ehcache.setDiskStorePath(diskStorePath);
	}

	public void setName(String name) {
		_ehcache.setName(name);
	}

	public void setNodeBulkLoadEnabled(boolean enabledBulkLoad)
		throws TerracottaNotRunningException, UnsupportedOperationException {

		_ehcache.setNodeBulkLoadEnabled(enabledBulkLoad);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public void setNodeCoherent(boolean nodeCoherent)
		throws UnsupportedOperationException {

		_ehcache.setNodeCoherent(nodeCoherent);
	}

	public void setSampledStatisticsEnabled(boolean sampleStatisticsEnabled) {
		_ehcache.setSampledStatisticsEnabled(sampleStatisticsEnabled);
	}

	public void setStatisticsAccuracy(int statisticsAccuracy) {
		_ehcache.setStatisticsAccuracy(statisticsAccuracy);
	}

	public void setStatisticsEnabled(boolean statisticsEnabled) {
		_ehcache.setStatisticsEnabled(statisticsEnabled);
	}

	public void setTransactionManagerLookup(
		TransactionManagerLookup transactionManagerLookup) {

		_ehcache.setTransactionManagerLookup(transactionManagerLookup);
	}

	public void setWrappedCache(Ehcache ehcache) {
		_ehcache = ehcache;
	}

	public boolean tryReadLockOnKey(Object key, long timeout)
		throws InterruptedException {

		return _ehcache.tryReadLockOnKey(key, timeout);
	}

	public boolean tryWriteLockOnKey(Object key, long timeout)
		throws InterruptedException {

		return _ehcache.tryWriteLockOnKey(key, timeout);
	}

	public void unregisterCacheExtension(CacheExtension cacheExtension) {
		_ehcache.unregisterCacheExtension(cacheExtension);
	}

	public void unregisterCacheLoader(CacheLoader cacheLoader) {
		_ehcache.unregisterCacheLoader(cacheLoader);
	}

	public void unregisterCacheWriter() {
		_ehcache.unregisterCacheWriter();
	}

	public void waitUntilClusterBulkLoadComplete()
		throws TerracottaNotRunningException, UnsupportedOperationException {

		_ehcache.waitUntilClusterBulkLoadComplete();
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public void waitUntilClusterCoherent()
		throws UnsupportedOperationException {

		_ehcache.waitUntilClusterCoherent();
	}

	protected boolean isStatusAlive() {
		Status status = _ehcache.getStatus();

		if (status.equals(Status.STATUS_ALIVE)) {
			return true;
		}
		else {
			return false;
		}
	}

	private Ehcache _ehcache;
	private AtomicInteger _referenceCounter = new AtomicInteger(0);

}