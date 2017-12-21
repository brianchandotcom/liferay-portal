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

package com.liferay.ant.heap.dump;

import com.liferay.petra.process.EchoOutputProcessor;
import com.liferay.portal.kernel.util.HeapUtil;

import java.io.File;

import java.util.concurrent.Future;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Shuyang Zhou
 */
public class HeapDumpTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			File dumpFile = getFile();

			Future<?> future = HeapUtil.heapDump(
				_live, true, dumpFile.getCanonicalPath(),
				EchoOutputProcessor.INSTANCE);

			future.get();

			log("Successfully dumped heap at " + dumpFile.getCanonicalPath());
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public File getFile() {
		if (_file == null) {
			return new File(
				System.getProperty("java.io.tmpdir"),
				"ant-process-" + HeapUtil.getProcessId() + "-heap-dump.bin");
		}

		return _file;
	}

	public void setFile(File file) {
		_file = file;
	}

	public void setLive(boolean live) {
		_live = live;
	}

	private File _file;
	private boolean _live;

}