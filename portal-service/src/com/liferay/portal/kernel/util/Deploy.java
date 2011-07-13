package com.liferay.portal.kernel.util;

import java.io.File;
import java.io.IOException;


public interface Deploy {

	public String getDeployDir() throws Exception;

	public void deploy(File source) throws Exception;

	public void deploy(File source, String context) throws Exception;

	public void redeploy(String context) throws Exception;

	public void undeploy(String context) throws Exception;

	public void hotDeployJetty(String context) throws IOException;

}
