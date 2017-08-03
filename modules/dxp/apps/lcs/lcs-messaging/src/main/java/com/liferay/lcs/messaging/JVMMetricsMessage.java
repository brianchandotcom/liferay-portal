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

package com.liferay.lcs.messaging;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Ivica Cardic
 */
public class JVMMetricsMessage extends MetricsMessage {

	public Map<String, Map<String, Long>> getBufferPoolMetrics() {
		return _bufferPoolMetrics;
	}

	public int getDaemonThreadCount() {
		return _daemonThreadCount;
	}

	public Set<String> getDeadlockedThreads() {
		return _deadlockedThreads;
	}

	public Double getFileDescriptorUsage() {
		return _fileDescriptorUsage;
	}

	public Map<String, Map<String, Long>> getGarbageCollectorMetrics() {
		return _garbageCollectorMetrics;
	}

	public double getHeapCommitted() {
		return _heapCommitted;
	}

	public double getHeapInit() {
		return _heapInit;
	}

	public double getHeapMax() {
		return _heapMax;
	}

	public double getHeapUsage() {
		return _heapUsage;
	}

	public double getHeapUsed() {
		return _heapUsed;
	}

	public Map<String, Double> getMemoryPoolUsage() {
		return _memoryPoolUsage;
	}

	public String getName() {
		return _name;
	}

	public double getNonHeapUsage() {
		return _nonHeapUsage;
	}

	public int getThreadCount() {
		return _threadCount;
	}

	public Map<String, Double> getThreadStatePercentages() {
		return _threadStatePercentages;
	}

	public double getTotalCommitted() {
		return _totalCommitted;
	}

	public double getTotalInit() {
		return _totalInit;
	}

	public double getTotalMax() {
		return _totalMax;
	}

	public double getTotalUsed() {
		return _totalUsed;
	}

	public int getUptime() {
		return _uptime;
	}

	public String getVersion() {
		return _version;
	}

	public void setBufferPoolMetrics(
		Map<String, Map<String, Long>> bufferPoolMetrics) {

		_bufferPoolMetrics = bufferPoolMetrics;
	}

	public void setDaemonThreadCount(int daemonThreadCount) {
		_daemonThreadCount = daemonThreadCount;
	}

	public void setDeadlockedThreads(Set<String> deadlockedThreads) {
		_deadlockedThreads = deadlockedThreads;
	}

	public void setFileDescriptorUsage(Double fileDescriptorUsage) {
		_fileDescriptorUsage = fileDescriptorUsage;
	}

	public void setGarbageCollectorMetrics(
		Map<String, Map<String, Long>> garbageCollectorMetrics) {

		_garbageCollectorMetrics = garbageCollectorMetrics;
	}

	public void setHeapCommitted(double heapCommitted) {
		_heapCommitted = heapCommitted;
	}

	public void setHeapInit(double heapInit) {
		_heapInit = heapInit;
	}

	public void setHeapMax(double heapMax) {
		_heapMax = heapMax;
	}

	public void setHeapUsage(double heapUsage) {
		_heapUsage = heapUsage;
	}

	public void setHeapUsed(double heapUsed) {
		_heapUsed = heapUsed;
	}

	public void setMemoryPoolUsage(Map<String, Double> memoryPoolUsage) {
		_memoryPoolUsage = memoryPoolUsage;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNonHeapUsage(double nonHeapUsage) {
		_nonHeapUsage = nonHeapUsage;
	}

	public void setThreadCount(int threadCount) {
		_threadCount = threadCount;
	}

	public void setThreadStatePercentages(
		Map<String, Double> threadStatePercentages) {

		_threadStatePercentages = threadStatePercentages;
	}

	public void setTotalCommitted(double totalCommitted) {
		_totalCommitted = totalCommitted;
	}

	public void setTotalInit(double totalInit) {
		_totalInit = totalInit;
	}

	public void setTotalMax(double totalMax) {
		_totalMax = totalMax;
	}

	public void setTotalUsed(double totalUsed) {
		_totalUsed = totalUsed;
	}

	public void setUptime(int uptime) {
		_uptime = uptime;
	}

	public void setVersion(String version) {
		_version = version;
	}

	private Map<String, Map<String, Long>> _bufferPoolMetrics =
		new HashMap<String, Map<String, Long>>();
	private int _daemonThreadCount;
	private Set<String> _deadlockedThreads = new HashSet<String>();
	private Double _fileDescriptorUsage;
	private Map<String, Map<String, Long>> _garbageCollectorMetrics =
		new HashMap<String, Map<String, Long>>();
	private double _heapCommitted;
	private double _heapInit;
	private double _heapMax;
	private double _heapUsage;
	private double _heapUsed;
	private Map<String, Double> _memoryPoolUsage =
		new HashMap<String, Double>();
	private String _name;
	private double _nonHeapUsage;
	private int _threadCount;
	private Map<String, Double> _threadStatePercentages =
		new HashMap<String, Double>();
	private double _totalCommitted;
	private double _totalInit;
	private double _totalMax;
	private double _totalUsed;
	private int _uptime;
	private String _version;

}