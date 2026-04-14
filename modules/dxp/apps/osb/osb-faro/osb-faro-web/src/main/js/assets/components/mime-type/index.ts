import {ClassName, FILE_MIME_MAP, STRUCTURES_MAP} from './constants';

export const getMimeType = ({
	assetType,
	mimeType
}: {
	assetType?: string;
	mimeType?: string;
}): {className: ClassName; icon: string} => {
	if (!mimeType) {
		if (STRUCTURES_MAP[assetType || ClassName.DocumentDefault]) {
			return STRUCTURES_MAP[assetType || ClassName.DocumentDefault];
		}

		return STRUCTURES_MAP['CMSDocumentDefault'];
	}

	if (FILE_MIME_MAP[mimeType]) {
		return FILE_MIME_MAP[mimeType];
	}

	const prefix = mimeType.split('/')[0];

	if (FILE_MIME_MAP[prefix]) {
		return FILE_MIME_MAP[prefix];
	}

	return FILE_MIME_MAP['default'];
};
