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
public abstract class BaseCometHandler implements CometHandler {

	@Override
	public abstract CometHandler clone();

	@Override
	public void destroy() throws CometException {
		_cometState = CometState.STATE_CLOSED;

		try {
			doDestroy();
		}
		catch (CometException ce) {
			throw ce;
		}
		catch (Exception e) {
			throw new CometException(e);
		}
	}

	@Override
	public CometSession getCometSession() {
		return _cometSession;
	}

	@Override
	public CometState getCometState() {
		return _cometState;
	}

	@Override
	public void init(CometSession cometSession) throws CometException {
		_cometSession = cometSession;
		_cometState = CometState.STATE_READY;

		try {
			doInit(cometSession);
		}
		catch (CometException ce) {
			throw ce;
		}
		catch (Exception e) {
			throw new CometException(e);
		}
	}

	@Override
	public void receiveData(char[] data) throws CometException {
		receiveData(new String(data));
	}

	protected void doDestroy() throws Exception {
	}

	protected void doInit(CometSession cometSession) throws Exception {
	}

	private CometSession _cometSession;
	private CometState _cometState = CometState.STATE_OPEN;

}