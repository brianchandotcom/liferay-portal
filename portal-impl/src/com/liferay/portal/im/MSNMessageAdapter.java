/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.im;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import rath.msnm.MSNMessenger;
import rath.msnm.SwitchboardSession;
import rath.msnm.entity.MsnFriend;
import rath.msnm.event.MsnAdapter;
import rath.msnm.msg.MimeMessage;

/**
 * @author Brian Wing Shun Chan
 */
public class MSNMessageAdapter extends MsnAdapter {

	public MSNMessageAdapter(MSNMessenger msn, String to, String msg) {
		_msn = msn;
		_to = to;
		_msg = msg;
	}

	@Override
	public void whoJoinSession(SwitchboardSession ss, MsnFriend join) {
		try {
			if (_to.equals(join.getLoginName())) {
				ss.sendInstantMessage(new MimeMessage(_msg));
				ss.cleanUp();
			}
		}
		catch (Exception e) {
			_log.warn(e);
		}

		_msn.removeMsnListener(this);
	}

	private static Log _log = LogFactoryUtil.getLog(MSNMessageAdapter.class);

	private String _msg;
	private MSNMessenger _msn;
	private String _to;

}