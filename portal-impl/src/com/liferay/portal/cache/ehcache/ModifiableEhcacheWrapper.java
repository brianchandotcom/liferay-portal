/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import net.sf.ehcache.Status;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.event.RegisteredEventListeners;
import net.sf.ehcache.exceptionhandler.CacheExceptionHandler;
import net.sf.ehcache.extension.CacheExtension;
import net.sf.ehcache.loader.CacheLoader;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.attribute.DynamicAttributesExtractor;
import net.sf.ehcache.statistics.StatisticsGateway;
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

	@Override
	public void acquireReadLockOnKey(Object key) {
		_ehcache.acquireReadLockOnKey(key);
	}

	@Override
	public void acquireWriteLockOnKey(Object key) {
		_ehcache.acquireWriteLockOnKey(key);
	}

	@Override
	public void addPropertyChangeListener(
		PropertyChangeListener propertyChangeListener) {

		_ehcache.addPropertyChangeListener(propertyChangeListener);
	}

	public void addReference() {
		_referenceCounter.incrementAndGet();
	}

	@Override
	public void bootstrap() {
		_ehcache.bootstrap();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	@Override
	public long calculateInMemorySize()
		throws CacheException, IllegalStateException {

		return _ehcache.calculateInMemorySize();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	@Override
	public long calculateOffHeapSize()
		throws CacheException, IllegalStateException {

		return _ehcache.calculateOffHeapSize();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	@Override
	public long calculateOnDiskSize()
		throws CacheException, IllegalStateException {

		return _ehcache.calculateOnDiskSize();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return _ehcache.clone();
	}

	@Override
	public Query createQuery() {
		return _ehcache.createQuery();
	}

	@Override
	public void disableDynamicFeatures() {
		_ehcache.disableDynamicFeatures();
	}

	@Override
	public void dispose() throws IllegalStateException {
		_ehcache.dispose();
	}

	@Override
	public boolean equals(Object object) {
		return _ehcache.equals(object);
	}

	@Override
	public void evictExpiredElements() {
		_ehcache.evictExpiredElements();
	}

	@Override
	public void flush() throws CacheException, IllegalStateException {
		_ehcache.flush();
	}

	@Override
	public Element get(Object key)
		throws CacheException, IllegalStateException {

		return _ehcache.get(key);
	}

	@Override
	public Element get(Serializable key)
		throws CacheException, IllegalStateException {

		return _ehcache.get(key);
	}

	public int getActiveReferenceCount() {
		return _referenceCounter.get();
	}

	@Override
	public Map<Object, Element> getAll(Collection<?> keys)
		throws CacheException, IllegalStateException, NullPointerException {

		return _ehcache.getAll(keys);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Map getAllWithLoader(Collection keys, Object argument)
		throws CacheException {

		return _ehcache.getAllWithLoader(keys, argument);
	}

	@Override
	public BootstrapCacheLoader getBootstrapCacheLoader() {
		return _ehcache.getBootstrapCacheLoader();
	}

	@Override
	public CacheConfiguration getCacheConfiguration() {
		return _ehcache.getCacheConfiguration();
	}

	@Override
	public RegisteredEventListeners getCacheEventNotificationService() {
		return _ehcache.getCacheEventNotificationService();
	}

	@Override
	public CacheExceptionHandler getCacheExceptionHandler() {
		return _ehcache.getCacheExceptionHandler();
	}

	@Override
	public CacheManager getCacheManager() {
		return _ehcache.getCacheManager();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	@Override
	public int getDiskStoreSize() throws IllegalStateException {
		return _ehcache.getDiskStoreSize();
	}

	@Override
	public String getGuid() {
		return _ehcache.getGuid();
	}

	@Override
	public Object getInternalContext() {
		return _ehcache.getInternalContext();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getKeys() throws CacheException, IllegalStateException {
		return _ehcache.getKeys();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	@Override
	@SuppressWarnings("rawtypes")
	public List getKeysNoDuplicateCheck() throws IllegalStateException {
		return _ehcache.getKeysNoDuplicateCheck();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getKeysWithExpiryCheck()
		throws CacheException, IllegalStateException {

		return _ehcache.getKeysWithExpiryCheck();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	@Override
	public long getMemoryStoreSize() throws IllegalStateException {
		return _ehcache.getMemoryStoreSize();
	}

	@Override
	public String getName() {
		return _ehcache.getName();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	@Override
	public long getOffHeapStoreSize() throws IllegalStateException {
		return _ehcache.getOffHeapStoreSize();
	}

	@Override
	public Element getQuiet(Object key)
		throws CacheException, IllegalStateException {

		return _ehcache.getQuiet(key);
	}

	@Override
	public Element getQuiet(Serializable key)
		throws CacheException, IllegalStateException {

		return _ehcache.getQuiet(key);
	}

	@Override
	public List<CacheExtension> getRegisteredCacheExtensions() {
		return _ehcache.getRegisteredCacheExtensions();
	}

	@Override
	public List<CacheLoader> getRegisteredCacheLoaders() {
		return _ehcache.getRegisteredCacheLoaders();
	}

	@Override
	public CacheWriter getRegisteredCacheWriter() {
		return _ehcache.getRegisteredCacheWriter();
	}

	@Override
	public <T> Attribute<T> getSearchAttribute(String attributeName)
		throws CacheException {

		return _ehcache.getSearchAttribute(attributeName);
	}

	@Override
	public int getSize() throws CacheException, IllegalStateException {
		return _ehcache.getSize();
	}

	@Override
	public StatisticsGateway getStatistics() throws IllegalStateException {
		return _ehcache.getStatistics();
	}

	@Override
	public Status getStatus() {
		return _ehcache.getStatus();
	}

	@Override
	public Element getWithLoader(
			Object key, CacheLoader cacheLoader, Object argument)
		throws CacheException {

		return _ehcache.getWithLoader(key, cacheLoader, argument);
	}

	public Ehcache getWrappedCache() {
		return _ehcache;
	}

	@Override
	public CacheWriterManager getWriterManager() {
		return _ehcache.getWriterManager();
	}

	@Override
	public boolean hasAbortedSizeOf() {
		return _ehcache.hasAbortedSizeOf();
	}

	@Override
	public int hashCode() {
		return _ehcache.hashCode();
	}

	@Override
	public void initialise() {
		_ehcache.initialise();
	}

	@Override
	public boolean isClusterBulkLoadEnabled()
		throws TerracottaNotRunningException, UnsupportedOperationException {

		return _ehcache.isClusterBulkLoadEnabled();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #isClusterBulkLoadEnabled}
	 */
	@Deprecated
	@Override
	public boolean isClusterCoherent() throws TerracottaNotRunningException {
		return _ehcache.isClusterBulkLoadEnabled();
	}

	@Override
	public boolean isDisabled() {
		return _ehcache.isDisabled();
	}

	@Override
	public boolean isElementInMemory(Object key) {
		return _ehcache.isElementInMemory(key);
	}

	@Override
	public boolean isElementInMemory(Serializable key) {
		return _ehcache.isElementInMemory(key);
	}

	@Override
	public boolean isElementOnDisk(Object key) {
		return _ehcache.isElementOnDisk(key);
	}

	@Override
	public boolean isElementOnDisk(Serializable key) {
		return _ehcache.isElementOnDisk(key);
	}

	@Override
	public boolean isExpired(Element element)
		throws IllegalStateException, NullPointerException {

		return _ehcache.isExpired(element);
	}

	@Override
	public boolean isKeyInCache(Object key) {
		return _ehcache.isKeyInCache(key);
	}

	@Override
	public boolean isNodeBulkLoadEnabled()
		throws TerracottaNotRunningException, UnsupportedOperationException {

		return _ehcache.isNodeBulkLoadEnabled();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #isNodeBulkLoadEnabled}
	 */
	@Deprecated
	@Override
	public boolean isNodeCoherent() throws TerracottaNotRunningException {
		return _ehcache.isNodeBulkLoadEnabled();
	}

	@Override
	public boolean isReadLockedByCurrentThread(Object key)
		throws UnsupportedOperationException {

		return _ehcache.isReadLockedByCurrentThread(key);
	}

	@Override
	public boolean isSearchable() {
		return _ehcache.isSearchable();
	}

	@Override
	public boolean isValueInCache(Object value) {
		return _ehcache.isValueInCache(value);
	}

	@Override
	public boolean isWriteLockedByCurrentThread(Object key)
		throws UnsupportedOperationException {

		return _ehcache.isWriteLockedByCurrentThread(key);
	}

	@Override
	public void load(Object key) throws CacheException {
		_ehcache.load(key);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void loadAll(Collection keys, Object argument)
		throws CacheException {

		_ehcache.loadAll(keys, argument);
	}

	@Override
	public void put(Element element)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		_ehcache.put(element);
	}

	@Override
	public void put(Element element, boolean doNotNotifyCacheReplicators)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		_ehcache.put(element, doNotNotifyCacheReplicators);
	}

	@Override
	public void putAll(Collection<Element> elements)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		_ehcache.putAll(elements);
	}

	@Override
	public Element putIfAbsent(Element element) throws NullPointerException {
		return _ehcache.putIfAbsent(element);
	}

	@Override
	public Element putIfAbsent(
			Element element, boolean doNotNotifyCacheReplicators)
		throws NullPointerException {

		return _ehcache.putIfAbsent(element, doNotNotifyCacheReplicators);
	}

	@Override
	public void putQuiet(Element element)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		_ehcache.putQuiet(element);
	}

	@Override
	public void putWithWriter(Element element)
		throws CacheException, IllegalArgumentException, IllegalStateException {

		_ehcache.putWithWriter(element);
	}

	@Override
	public void registerCacheExtension(CacheExtension cacheExtension) {
		_ehcache.registerCacheExtension(cacheExtension);
	}

	@Override
	public void registerCacheLoader(CacheLoader cacheLoader) {
		_ehcache.registerCacheLoader(cacheLoader);
	}

	@Override
	public void registerCacheWriter(CacheWriter cacheWriter) {
		_ehcache.registerCacheWriter(cacheWriter);
	}

	@Override
	public void registerDynamicAttributesExtractor(
		DynamicAttributesExtractor dynamicAttributesExtractor) {

		_ehcache.registerDynamicAttributesExtractor(dynamicAttributesExtractor);
	}

	@Override
	public void releaseReadLockOnKey(Object key) {
		_ehcache.releaseReadLockOnKey(key);
	}

	@Override
	public void releaseWriteLockOnKey(Object key) {
		_ehcache.releaseWriteLockOnKey(key);
	}

	@Override
	public boolean remove(Object key) throws IllegalStateException {
		return _ehcache.remove(key);
	}

	@Override
	public boolean remove(Object key, boolean doNotNotifyCacheReplicators)
		throws IllegalStateException {

		return _ehcache.remove(key, doNotNotifyCacheReplicators);
	}

	@Override
	public boolean remove(Serializable key) throws IllegalStateException {
		return _ehcache.remove(key);
	}

	@Override
	public boolean remove(Serializable key, boolean doNotNotifyCacheReplicators)
		throws IllegalStateException {

		return _ehcache.remove(key, doNotNotifyCacheReplicators);
	}

	@Override
	public void removeAll() throws CacheException, IllegalStateException {
		if (!isStatusAlive()) {
			return;
		}

		_ehcache.removeAll();
	}

	@Override
	public void removeAll(boolean doNotNotifyCacheReplicators)
		throws CacheException, IllegalStateException {

		if (!isStatusAlive()) {
			return;
		}

		_ehcache.removeAll(doNotNotifyCacheReplicators);
	}

	@Override
	public void removeAll(Collection<?> keys)
		throws IllegalStateException, NullPointerException {

		_ehcache.removeAll(keys);
	}

	@Override
	public void removeAll(
			Collection<?> keys, boolean doNotNotifyCacheReplicators)
		throws IllegalStateException, NullPointerException {

		_ehcache.removeAll(keys, doNotNotifyCacheReplicators);
	}

	@Override
	public boolean removeElement(Element element) throws NullPointerException {
		return _ehcache.removeElement(element);
	}

	@Override
	public void removePropertyChangeListener(
		PropertyChangeListener propertyChangeListener) {

		_ehcache.removePropertyChangeListener(propertyChangeListener);
	}

	@Override
	public boolean removeQuiet(Object key) throws IllegalStateException {
		if (!isStatusAlive()) {
			return true;
		}

		return _ehcache.removeQuiet(key);
	}

	@Override
	public boolean removeQuiet(Serializable key) throws IllegalStateException {
		if (!isStatusAlive()) {
			return true;
		}

		return _ehcache.removeQuiet(key);
	}

	public void removeReference() {
		_referenceCounter.decrementAndGet();
	}

	@Override
	public boolean removeWithWriter(Object key)
		throws CacheException, IllegalStateException {

		if (!isStatusAlive()) {
			return true;
		}

		return _ehcache.removeWithWriter(key);
	}

	@Override
	public Element replace(Element element) throws NullPointerException {
		return _ehcache.replace(element);
	}

	@Override
	public boolean replace(Element old, Element element)
		throws IllegalArgumentException, NullPointerException {

		return _ehcache.replace(old, element);
	}

	@Override
	public void setBootstrapCacheLoader(
			BootstrapCacheLoader bootstrapCacheLoader)
		throws CacheException {

		_ehcache.setBootstrapCacheLoader(bootstrapCacheLoader);
	}

	@Override
	public void setCacheExceptionHandler(
		CacheExceptionHandler cacheExceptionHandler) {

		_ehcache.setCacheExceptionHandler(cacheExceptionHandler);
	}

	@Override
	public void setCacheManager(CacheManager cacheManager) {
		_ehcache.setCacheManager(cacheManager);
	}

	@Override
	public void setDisabled(boolean disabled) {
		_ehcache.setDisabled(disabled);
	}

	@Override
	public void setName(String name) {
		_ehcache.setName(name);
	}

	@Override
	public void setNodeBulkLoadEnabled(boolean enabledBulkLoad)
		throws TerracottaNotRunningException, UnsupportedOperationException {

		_ehcache.setNodeBulkLoadEnabled(enabledBulkLoad);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link
	 *             #setNodeBulkLoadEnabled(boolean)}
	 */
	@Deprecated
	@Override
	public void setNodeCoherent(boolean nodeCoherent)
		throws TerracottaNotRunningException, UnsupportedOperationException {

		_ehcache.setNodeBulkLoadEnabled(nodeCoherent);
	}

	@Override
	public void setTransactionManagerLookup(
		TransactionManagerLookup transactionManagerLookup) {

		_ehcache.setTransactionManagerLookup(transactionManagerLookup);
	}

	public void setWrappedCache(Ehcache ehcache) {
		_ehcache = ehcache;
	}

	@Override
	public String toString() {
		return _ehcache.toString();
	}

	@Override
	public boolean tryReadLockOnKey(Object key, long timeout)
		throws InterruptedException {

		return _ehcache.tryReadLockOnKey(key, timeout);
	}

	@Override
	public boolean tryWriteLockOnKey(Object key, long timeout)
		throws InterruptedException {

		return _ehcache.tryWriteLockOnKey(key, timeout);
	}

	@Override
	public void unregisterCacheExtension(CacheExtension cacheExtension) {
		_ehcache.unregisterCacheExtension(cacheExtension);
	}

	@Override
	public void unregisterCacheLoader(CacheLoader cacheLoader) {
		_ehcache.unregisterCacheLoader(cacheLoader);
	}

	@Override
	public void unregisterCacheWriter() {
		_ehcache.unregisterCacheWriter();
	}

	@Override
	public void waitUntilClusterBulkLoadComplete()
		throws TerracottaNotRunningException, UnsupportedOperationException {

		_ehcache.waitUntilClusterBulkLoadComplete();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link
	 *             #waitUntilClusterBulkLoadComplete}
	 */
	@Deprecated
	@Override
	public void waitUntilClusterCoherent()
		throws TerracottaNotRunningException, UnsupportedOperationException {

		_ehcache.waitUntilClusterBulkLoadComplete();
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