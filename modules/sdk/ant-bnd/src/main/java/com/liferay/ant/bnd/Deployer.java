/* @generated */
/**
 * This class is mostly a copy of methods from
 * https://github.com/bndtools/bnd/blob/2.4.1.REL/biz.aQute.bndlib/src/aQute/bnd/build/Project.java
 * with small adaptations. As such it is licensed under the terms of it's
 * project.
 */

package com.liferay.ant.bnd;

import aQute.bnd.build.ProjectMessages;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Processor;
import aQute.bnd.service.RepositoryPlugin;

import aQute.libg.reporter.ReporterMessages;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.util.List;

public class Deployer {

	private final Processor processor;
	final ProjectMessages		msgs;

	public Deployer(Processor processor) {
		this.processor = processor;

		msgs = ReporterMessages.base(this.processor, ProjectMessages.class);
	}

	/**
	 * Deploy the file (which must be a bundle) into the repository.
	 *
	 * @param file
	 *            bundle
	 */
	public void deploy(File file) throws Exception {
		String name = processor.getProperty(Constants.DEPLOYREPO);
		deploy(name, file);
	}

	/**
	 * Deploy the file (which must be a bundle) into the repository.
	 *
	 * @param name
	 *            The repository name
	 * @param file
	 *            bundle
	 */
	public void deploy(String name, File file) throws Exception {
		List<RepositoryPlugin> plugins = processor.getPlugins(RepositoryPlugin.class);

		RepositoryPlugin rp = null;
		for (RepositoryPlugin plugin : plugins) {
			if (!plugin.canWrite()) {
				continue;
			}
			if (name == null) {
				rp = plugin;
				break;
			} else if (name.equals(plugin.getName())) {
				rp = plugin;
				break;
			}
		}

		if (rp != null) {
			try {
				rp.put(new BufferedInputStream(new FileInputStream(file)), new RepositoryPlugin.PutOptions());
				return;
			}
			catch (Exception e) {
				msgs.DeployingFile_On_Exception_(file, rp.getName(), e);
			}
			return;
		}
		processor.trace("No repo found " + file);
		throw new IllegalArgumentException("No repository found for " + file);
	}

}