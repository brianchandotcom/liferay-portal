package com.liferay.ratings.taglib.servlet.taglib;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.ratings.kernel.RatingsType;
import com.liferay.ratings.kernel.definition.PortletRatingsDefinitionUtil;
import com.liferay.ratings.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Ambrín Chaudhary
 */
public class RatingsTag extends IncludeTag {

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_classPK = 0;
		_type = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected String getType(HttpServletRequest httpServletRequest) {
		if (Validator.isNotNull(_type)) {
			return _type;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getSiteGroup();

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		RatingsType ratingsType = null;

		if (group != null) {
			try {
				ratingsType = PortletRatingsDefinitionUtil.getRatingsType(
					themeDisplay.getCompanyId(), group.getGroupId(),
					_className);
			}
			catch (PortalException portalException) {
				_log.error(
					"Unable to get ratings type for group " +
						group.getGroupId(),
					portalException);
			}
		}

		if (ratingsType == null) {
			ratingsType = RatingsType.STARS;
		}

		return ratingsType.getValue();
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ratings:ratings:type", getType(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-ui:ratings:className", _className);
		httpServletRequest.setAttribute(
			"liferay-ui:ratings:classPK", String.valueOf(_classPK));
	}

	private static final String _PAGE = "/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(RatingsTag.class);

	private String _className;
	private long _classPK;
	private String _type;

}