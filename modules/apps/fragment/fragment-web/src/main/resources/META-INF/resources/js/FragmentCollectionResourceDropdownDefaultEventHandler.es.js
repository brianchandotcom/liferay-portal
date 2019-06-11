import {DefaultEventHandler} from 'frontend-js-web';

class FragmentCollectionResourceDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteFragmentCollectionResource(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(
				document.hrefFm,
				itemData.deleteFragmentCollectionResourceURL
			);
		}
	}
}

export default FragmentCollectionResourceDropdownDefaultEventHandler;
