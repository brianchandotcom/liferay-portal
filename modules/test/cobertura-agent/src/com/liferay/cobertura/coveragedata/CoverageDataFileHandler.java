//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.liferay.cobertura.coveragedata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import net.sourceforge.cobertura.coveragedata.HasBeenInstrumented;
import net.sourceforge.cobertura.util.ConfigurationUtil;

public abstract class CoverageDataFileHandler implements HasBeenInstrumented {
	private static File defaultFile = null;

	public static File getDefaultDataFile() {
		if(defaultFile != null) {
			return defaultFile;
		} else {
			ConfigurationUtil config = new ConfigurationUtil();
			defaultFile = new File(config.getDatafile());
			return defaultFile;
		}
	}

}