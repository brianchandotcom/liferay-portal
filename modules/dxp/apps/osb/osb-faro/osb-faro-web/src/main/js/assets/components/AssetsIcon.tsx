import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import getCN from 'classnames';
import React from 'react';

export enum MimeTypes {
	BasicWebContent = 'basic-web-content',
	Blog = 'blog',
	CustomStructure = 'custom-structure',
	DocumentCode = 'document-code',
	DocumentCompressed = 'document-compressed',
	DocumentDefault = 'document-default',
	DocumentImage = 'document-image',
	DocumentMultimedia = 'document-multimedia',
	DocumentPresentation = 'document-presentation',
	DocumentTable = 'document-table',
	DocumentText = 'document-text',
	DocumentVector = 'document-vector',
	Folder = 'folder',
	KnowledgeBase = 'knowledge-base'
}

function getAssetIcon(mimeType: MimeTypes) {
	switch (mimeType) {
		case MimeTypes.DocumentCode:
			return {
				className: 'asset-icon-document-code',
				icon: 'code'
			};
		case MimeTypes.DocumentCompressed:
			return {
				className: 'asset-icon-document-compressed',
				icon: 'document-compressed'
			};
		case MimeTypes.DocumentPresentation:
			return {
				className: 'asset-icon-document-presentation',
				icon: 'document-presentation'
			};
		case MimeTypes.DocumentTable:
			return {
				className: 'asset-icon-document-table',
				icon: 'document-table'
			};
		case MimeTypes.DocumentText:
			return {
				className: 'asset-icon-document-text',
				icon: 'document-text'
			};
		case MimeTypes.DocumentVector:
			return {
				className: 'asset-icon-document-vector',
				icon: 'document-vector'
			};
		case MimeTypes.DocumentImage:
			return {
				className: 'asset-icon-document-image',
				icon: 'document-image'
			};
		case MimeTypes.DocumentMultimedia:
			return {
				className: 'asset-icon-document-multimedia',
				icon: 'document-multimedia'
			};
		case MimeTypes.DocumentDefault:
			return {
				className: 'asset-icon-document-default',
				icon: 'document-default'
			};
		case MimeTypes.BasicWebContent:
			return {
				className: 'asset-icon-basic-content',
				icon: 'forms'
			};
		case MimeTypes.Blog:
			return {
				className: 'asset-icon-blog',
				icon: 'blogs'
			};
		case MimeTypes.KnowledgeBase:
			return {
				className: 'asset-icon-knowledge-base',
				icon: 'wiki'
			};
		case MimeTypes.CustomStructure:
			return {
				className: 'asset-icon-custom-structure',
				icon: 'web-content'
			};
		case MimeTypes.Folder: {
			return {
				className: 'asset-icon-folder',
				icon: 'folder'
			};
		}
		default:
			return {
				className: 'file-icon-color-0',
				icon: 'document-default'
			};
	}
}

interface IAssetIconProps extends React.HTMLAttributes<HTMLElement> {
	mimeType: MimeTypes;
}

const AssetIcon: React.FC<IAssetIconProps> = ({className, mimeType}) => {
	const icon = getAssetIcon(mimeType as MimeTypes);

	return (
		<ClaySticker
			className={getCN(icon.className, className)}
			displayType='dark'
		>
			<ClayIcon symbol={icon.icon} />
		</ClaySticker>
	);
};

export {AssetIcon};
