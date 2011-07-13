package com.liferay.portal.kernel.util;

import java.io.File;
import java.io.IOException;


public class DeployUtil {

	public static String getDeployDir() throws Exception {
		return _deploy.getDeployDir();
	}

	public static void deploy(File source) throws Exception {
		_deploy.deploy(source);
	}

	public static void deploy(File source, String context) throws Exception {
		_deploy.deploy(source, context);
	}

	public static void redeploy(String context) throws Exception {
		_deploy.redeploy(context);
	}

	public static void undeploy(String context) throws Exception {
		_deploy.undeploy(context);
	}

	public static void hotDeployJetty(String context) throws IOException {
		_deploy.hotDeployJetty(context);
	}

	public static Deploy getDeploy() {
		return _deploy;
	}

	public void setDeploy(Deploy deploy) {
		_deploy = deploy;
	}

	private static Deploy _deploy;

}
