package com.liferay.mentions.web.internal.editor.configuration;


import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import org.osgi.service.component.annotations.Component;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	property = {
		"editor.config.key=pageEditorCommentEditor",
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"service.ranking:Integer=10"
	},
	service = EditorConfigContributor.class
)
public class PageCommentsMentionsEditorConfigContributor extends BaseMentionsEditorConfigContributor {
}
