import DXPLinkButton from './DXPLinkButton';
import React from 'react';
import {sub} from 'shared/util/lang';

interface IPublishVariantProps
	extends React.HtmlHTMLAttributes<React.ElementType> {
	dxpVariantName: string;
	link: string;
}

const PublishVariant: React.FC<IPublishVariantProps> = ({
	dxpVariantName,
	link
}) => (
	<>
		<div className='mb-3 font-size-md font-weight-semibold'>
			{sub(Liferay.Language.get('are-you-sure-you-want-to-publish-x'), [
				dxpVariantName
			])}
		</div>

		<DXPLinkButton href={link} />

		<div className='mt-3'>
			{Liferay.Language.get(
				'this-will-publish-the-variant-as-the-live-experience-for-the-target-segment-that-was-chosen-for-this-test'
			)}
		</div>
	</>
);

export default PublishVariant;
