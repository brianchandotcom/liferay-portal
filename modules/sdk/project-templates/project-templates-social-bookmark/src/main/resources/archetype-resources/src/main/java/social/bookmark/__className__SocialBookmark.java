package ${package}.social.bookmark;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.social.bookmarks.SocialBookmark;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = {
		"social.bookmarks.type=${socialBookmarkType}"
	}
)
public class ${className}SocialBookmark implements SocialBookmark {

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "${socialBookmarkType}");
	}

	@Override
	public String getPostURL(String title, String url) {
		return String.format("http://www.google.com/search?q=%s", url);
	}

	@Override
	public void render(
			String target, String title, String url, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/page.jsp");

		requestDispatcher.include(request, response);
	}

	@Reference(
		target = "(bundle.symbolic.name=${package}.social.bookmark.${socialBookmarkType})"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference(
		target = "(osgi.web.symbolicname=${package}.social.bookmark.${socialBookmarkType})"
	)
	private ServletContext _servletContext;

}
