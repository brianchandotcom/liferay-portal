/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {SidePanel} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {addParams} from 'frontend-js-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import AssetPreview from '../../common/components/AssetPreview';
import Toolbar from '../../common/components/Toolbar';
import {ISearchAssetObjectEntry} from '../../common/types/AssetType';
import {SharingPermission} from '../../common/types/SharingPermission';
import CommentsPanel from '../../content_editor/components/panels/CommentsPanel';
import ObjectEntryService from '../info_panel/services/ObjectEntryService';

import '../../../css/view_asset/ViewAsset.scss';

interface CommentsProps {
	addCommentURL: string;
	deleteCommentURL: string;
	editCommentURL: string;
	editorConfig: any;
	getCommentsURL: string;
}

interface ViewAssetProps {
	backURL: string;
	className: string;
	commentsProps: CommentsProps;
	contentViewURL: string;
	getObjectEntryURL: string;
}

const ViewAssetCommentsPanel = ({
	commentsProps,
	item,
}: {
	commentsProps: CommentsProps;
	item: ISearchAssetObjectEntry;
}) => {
	const {addCommentURL, editCommentURL, getCommentsURL} = commentsProps;
	const {
		embedded: {id},
		entryClassName,
	}: ISearchAssetObjectEntry = item;

	const params = `?className=${encodeURIComponent(entryClassName)}&classPK=${id}`;

	return (
		<>
			<h3 className="font-weight-semi-bold px-3 py-4 text-7">
				{Liferay.Language.get('comments')}
			</h3>

			<CommentsPanel
				{...commentsProps}
				addCommentURL={addParams(params, addCommentURL)}
				editCommentURL={addParams(params, editCommentURL)}
				getCommentsURL={addParams(params, getCommentsURL)}
			></CommentsPanel>
		</>
	);
};

export default function ViewAsset({
	backURL,
	className,
	commentsProps,
	contentViewURL,
	getObjectEntryURL,
}: ViewAssetProps) {
	const [itemState, setItemState] = useState<ISearchAssetObjectEntry | null>(
		null
	);

	useEffect(() => {
		if (getObjectEntryURL) {
			ObjectEntryService.getObjectEntry(getObjectEntryURL).then(
				({data}) => {
					if (data) {
						setItemState(data as any);
					}
				}
			);
		}
	}, [getObjectEntryURL]);

	const item: ISearchAssetObjectEntry = useMemo(() => {
		if (!itemState) {
			return null;
		}

		if (!(itemState as any).embedded) {
			return transformItem(itemState, className);
		}

		return itemState;
	}, [className, itemState]);

	const [openSidePanel, setOpenSidePanel] = useState(false);
	const containerRef = useRef(null);

	const handleClickComments = () => {
		setOpenSidePanel(!openSidePanel);
	};

	if (!item) {
		return (
			<div className="align-items-center d-flex h-100 justify-content-center w-100">
				<ClayLoadingIndicator />
			</div>
		);
	}

	const file = item.embedded.file;
	const link = file?.link;

	const showCommentsPanel =
		item.actionIds?.includes(SharingPermission.Comment) ?? true;

	return (
		<>
			<Toolbar backURL={backURL} title={item.title}>
				<Toolbar.Item>
					<div className="align-items-center c-gap-2 d-flex">
						{showCommentsPanel && (
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'show-comments'
								)}
								borderless
								className={classNames({
									active: 'commentPanel',
								})}
								displayType="secondary"
								onClick={handleClickComments}
								symbol="message"
							/>
						)}

						{link?.href && (
							<div className="autofit-col pr-3">
								<ClayLink
									button
									displayType="primary"
									href={link.href}
									small
								>
									<span className="inline-item inline-item-before">
										<ClayIcon symbol="download" />
									</span>

									{Liferay.Language.get('download')}
								</ClayLink>
							</div>
						)}
					</div>
				</Toolbar.Item>
			</Toolbar>

			<div className="h-100" ref={containerRef}>
				<div className="d-flex h-100">
					<div className="justify-content-center mx-6 view-asset-preview-container w-100">
						<AssetPreview item={item} url={contentViewURL} />
					</div>
				</div>

				<SidePanel
					containerRef={containerRef}
					onOpenChange={setOpenSidePanel}
					open={openSidePanel}
				>
					{showCommentsPanel && (
						<ViewAssetCommentsPanel
							commentsProps={commentsProps}
							item={item}
						/>
					)}
				</SidePanel>
			</div>
		</>
	);
}

export function transformItem(item: any, className?: string) {
	return {
		...item,
		embedded: item.embedded || {
			file: item.file ?? undefined,
			id: item.id,
		},
		entryClassName: className,
	};
}
