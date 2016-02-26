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

package com.liferay.portal.kernel.poller.comet;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public interface CometHandler {

	public CometHandler clone();

	public void destroy() throws CometException;

	public CometSession getCometSession();

	public CometState getCometState();

	public void init(CometSession cometSession) throws CometException;

	public void receiveData(char[] data) throws CometException;

	public void receiveData(String data) throws CometException;

}