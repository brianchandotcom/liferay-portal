/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.image.HookFactory;
import com.liferay.portal.kernel.image.Hook;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageImpl extends ImageBaseImpl {

	public ImageImpl() {
	}

	public byte[] getTextObj() {
		if (_textObj == null) {
			try {
				Hook hook = HookFactory.getInstance();

				_textObj = hook.getImageAsBytes(this);
			}
			catch (Exception e) {
				_log.error("Error reading image " + getImageId(), e);
			}
		}

		return _textObj;
	}

	public void setTextObj(byte[] textObj) {
		_textObj = textObj;

		super.setText(Base64.objectToString(textObj));
	}

	private byte[] _textObj;

	private static Log _log = LogFactoryUtil.getLog(ImageImpl.class);

}