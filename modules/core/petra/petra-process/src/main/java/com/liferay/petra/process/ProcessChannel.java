/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process;

import com.liferay.petra.concurrent.NoticeableFuture;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public interface ProcessChannel<T extends Serializable> {

	public NoticeableFuture<T> getProcessNoticeableFuture();

	public <V extends Serializable> NoticeableFuture<V> write(
		ProcessCallable<V> processCallable);

}