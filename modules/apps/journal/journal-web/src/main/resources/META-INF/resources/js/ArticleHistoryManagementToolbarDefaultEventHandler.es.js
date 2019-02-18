import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class ArticleHistoryManagementToolbarDefaultEventHandler extends DefaultEventHandler {
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
}

export default ArticleHistoryManagementToolbarDefaultEventHandler;