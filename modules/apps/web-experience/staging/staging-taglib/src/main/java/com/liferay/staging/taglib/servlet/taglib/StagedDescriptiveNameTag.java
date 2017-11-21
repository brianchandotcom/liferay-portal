package com.liferay.staging.taglib.servlet.taglib;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.jsp.JspWriter;

public class StagedDescriptiveNameTag extends IncludeTag {

	@Override
	public void cleanUp() {
		_group = null;
	}

	public void setGroup(Group group) {
		_group = group;
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write(_getStagedDescriptiveName());

		return EVAL_PAGE;
	}

	private String _getStagedDescriptiveName() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String stagedDescriptiveName = "";

		try {
			stagedDescriptiveName = HtmlUtil.escape(
				_group.getDescriptiveName(themeDisplay.getLocale()));

			if (_group.isStaged() && !_group.isStagedRemotely() &&
				_group.isStagingGroup()) {

				stagedDescriptiveName +=
					" (" + LanguageUtil.get(request, "staging") + ")";
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe);
			}
		}

		return stagedDescriptiveName;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedDescriptiveNameTag.class);

	private Group _group;

}