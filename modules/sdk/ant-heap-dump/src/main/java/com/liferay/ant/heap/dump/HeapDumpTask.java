/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (Exception exception) {
			throw new BuildException(exception);
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