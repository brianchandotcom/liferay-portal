import {AssetNames, AssetTypes} from 'shared/util/constants';
import {List} from 'immutable';
import {Property} from 'shared/util/records';
import {PropertyTypes} from '../constants';

const createWebProperty = ({
	entityType,
	label,
	name,
	type = PropertyTypes.Behavior
}: {
	entityType: AssetTypes;
	label: string;
	name: AssetNames;
	type: PropertyTypes;
}): Property =>
	new Property({
		entityName: Liferay.Language.get('individual'),
		entityType,
		label,
		name,
		propertyKey: 'web',
		type
	});

const WEB_BEHAVIORS = List(
	[
		{
			entityType: AssetTypes.Document,
			label: Liferay.Language.get('downloaded-document-&-media'),
			name: AssetNames.DocumentDownloaded
		},
		{
			entityType: AssetTypes.Form,
			label: Liferay.Language.get('submitted-form'),
			name: AssetNames.FormSubmitted
		},
		{
			entityType: AssetTypes.Form,
			label: Liferay.Language.get('viewed-form'),
			name: AssetNames.FormViewed
		},
		{
			entityType: AssetTypes.WebPage,
			label: Liferay.Language.get('viewed-page'),
			name: AssetNames.PageViewed
		},
		{
			entityType: AssetTypes.Blog,
			label: Liferay.Language.get('commented-on-blog'),
			name: AssetNames.CommentPosted
		},
		{
			entityType: AssetTypes.Blog,
			label: Liferay.Language.get('viewed-blog'),
			name: AssetNames.BlogViewed
		},
		{
			entityType: AssetTypes.Document,
			label: Liferay.Language.get('viewed-document-&-media'),
			name: AssetNames.DocumentPreviewed
		},
		{
			entityType: AssetTypes.WebContent,
			label: Liferay.Language.get('viewed-web-content'),
			name: AssetNames.WebContentViewed
		}
	].map(createWebProperty)
);

export default WEB_BEHAVIORS;
