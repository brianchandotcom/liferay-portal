package com.liferay.dispatch.service;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

/**
 * @author Matija Petanjek
 */
public interface ScheduledTaskExecutorService {

	/**
	 * This method returns the name of the process type
	 */
	public String getName();

	/**
	 * This method execute the selected process
	 *
	 * @param dispatchTriggerId
	 * @throws IOException
	 * @throws PortalException
	 */
	public void runProcess(long dispatchTriggerId)
		throws IOException, PortalException;
}
