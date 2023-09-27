/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeSCSSMixinsCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		Matcher matcher = _mixinPattern.matcher(content);

		while (matcher.find()) {
			String method = matcher.group(1);
			String mixin = matcher.group(2);

			if (mixin.equals("media-query")) {
				content = StringUtil.replace(
					content, method, _replaceMediaQuery(fileName, method));
			}
			else if (mixin.equals("respond-to")) {
				content = StringUtil.replace(
					content, method, _replaceRespondTo(method));
			}

			content = StringUtil.replace(
				content, method, _replaceGenericType(method));
		}

		return content;
	}

	@Override
	protected String[] getValidExtensions() {
		return new String[] {"scss"};
	}

	private String _replaceGenericType(String mixin) {
		String methodCall = mixin.substring(
			0, mixin.indexOf(StringPool.OPEN_PARENTHESIS));

		if (methodCall.equals("lg")) {
			return "media-breakpoint-up(xl)";
		}
		else if (methodCall.equals("md")) {
			return "media-breakpoint-up(lg)";
		}
		else if (methodCall.equals("sm")) {
			return "media-breakpoint-up(md)";
		}
		else if (methodCall.equals("xs")) {
			return "media-breakpoint-up(sm)";
		}

		return mixin;
	}

	private String _replaceMediaQuery(String fileName, String mixin) {
		List<String> parameterList = JavaSourceUtil.getParameterList(mixin);

		if (parameterList.size() != 1) {
			String message = StringBundler.concat(
				"Do not use 'media-query' mixing, replace with its equivalent ",
				"(e.g., media-breakpoint-up, media-breakpoint-only, ",
				"media-breakpoint-down, etc.), see LPS-194507.");

			addMessage(fileName, message);
		}
		else if (parameterList.contains("md")) {
			return "media-breakpoint-up(lg)";
		}
		else if (parameterList.contains("sm")) {
			return "media-breakpoint-up(md)";
		}

		return mixin;
	}

	private String _replaceRespondTo(String mixin) {
		List<String> parameterList = JavaSourceUtil.getParameterList(mixin);

		if (parameterList.contains("desktop")) {
			if (parameterList.contains("tablet")) {
				return "media-breakpoint-up(md)";
			}

			return "media-breakpoint-up(lg)";
		}
		else if (parameterList.contains("phone")) {
			if (parameterList.contains("tablet")) {
				return "media-breakpoint-down(md)";
			}

			return "media-breakpoint-down(sm)";
		}
		else if (parameterList.contains("tablet")) {
			return "media-breakpoint-only(md)";
		}

		return mixin;
	}

	private static final Pattern _mixinPattern = Pattern.compile(
		"@include\\s*(([\\w+-]+)\\((.+)*\\))");

}