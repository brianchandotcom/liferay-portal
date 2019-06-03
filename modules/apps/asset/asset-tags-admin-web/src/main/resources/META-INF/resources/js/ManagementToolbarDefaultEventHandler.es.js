import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class ManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteTags() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	mergeTags(itemData) {
		let mergeURL = itemData.mergeTagsURL;

		location.href = mergeURL.replace(
			escape('[$MERGE_TAGS_IDS$]'),
			Liferay.Util.listCheckedExcept(
				this.one('#fm'),
				this.ns('allRowIds')
			)
		);
	}
}

export default ManagementToolbarDefaultEventHandler;
