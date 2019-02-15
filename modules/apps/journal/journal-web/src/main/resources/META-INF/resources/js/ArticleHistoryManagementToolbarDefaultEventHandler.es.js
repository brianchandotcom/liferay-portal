import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class ArticleHistoryManagementToolbarDefaultEventHandler extends PortletBase {
	callAction(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteArticles(itemData) {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-version'))) {
			submitForm(this.one('#fm'), itemData.deleteArticlesURL);
		}
	}

	expireArticles(itemData) {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-expire-the-selected-version'))) {
			submitForm(this.one('#fm'), itemData.expireArticlesURL);
		}
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}
}

ArticleHistoryManagementToolbarDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default ArticleHistoryManagementToolbarDefaultEventHandler;