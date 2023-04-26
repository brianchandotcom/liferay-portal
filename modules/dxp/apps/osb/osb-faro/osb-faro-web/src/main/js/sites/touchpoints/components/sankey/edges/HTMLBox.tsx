import BasePage from 'shared/components/base-page';
import ClayButton from '@clayui/button';
import React, {useContext, useState} from 'react';
import TextTruncate from 'shared/components/TextTruncate';
import {AssetNode, SankeyNode} from '../utils/types';
import {CLASSNAME_BOX} from './EmptyStateEdge';
import {getAssetUrl} from '../utils/edges';
import {Link} from 'react-router-dom';
import {sub} from 'shared/util/lang';
import {toThousands} from 'shared/util/numbers';

interface IHTMLBoxProps extends React.HTMLAttributes<HTMLElement> {
	color: string;
	items?: Array<AssetNode>;
	node: SankeyNode;
}

const HTMLBox: React.FC<IHTMLBoxProps> = ({color, items = [], node}) => {
	const [showAssets, setShowAssets] = useState<boolean>(false);
	const handleToggleShowAssets = () => setShowAssets(!showAssets);
	const {router} = useContext(BasePage.Context);

	const {directAccessMetric, name} = node;

	const renderListGroup = () => {
		const {url: parentUrl} = node;

		const assets = items.map(item => ({
			...item,
			id: item.assetId,
			type: item.assetType
		}));

		if (assets.length <= 1) {
			const asset = assets[0];
			const url = getAssetUrl(asset, parentUrl, router);

			return (
				<Link className='icon icon-asset' to={url}>
					<TextTruncate
						inline
						maxCharLength={20}
						title={asset.title}
					/>
				</Link>
			);
		}

		return (
			<ul>
				{showAssets ? (
					<>
						{assets.map((asset, index) => {
							const {title} = asset;
							const url = getAssetUrl(asset, parentUrl, router);

							return (
								<li key={index}>
									<Link className='icon icon-asset' to={url}>
										<TextTruncate
											inline
											maxCharLength={20}
											title={title}
										/>
									</Link>
								</li>
							);
						})}

						<li>
							<ClayButton
								className='button-root icon icon-minor action-link'
								displayType='unstyled'
								onClick={handleToggleShowAssets}
							>
								{Liferay.Language.get('close-list')}
							</ClayButton>
						</li>
					</>
				) : (
					<li>
						<ClayButton
							className='action-link button-root icon icon-plus'
							displayType='unstyled'
							onClick={handleToggleShowAssets}
						>
							{sub(Liferay.Language.get('show-top-x-assets'), [
								assets.length
							])}
						</ClayButton>
					</li>
				)}
			</ul>
		);
	};

	return (
		<div className={`${CLASSNAME_BOX}-box`}>
			<div className={`${CLASSNAME_BOX}-content`}>
				<div className={`${CLASSNAME_BOX}-value`}>
					{sub(Liferay.Language.get('x-views'), [
						toThousands(directAccessMetric)
					])}
				</div>

				<div className={`${CLASSNAME_BOX}-title`}>
					<span className='text-truncate'>
						{name || Liferay.Language.get('untitled')}
					</span>
				</div>

				{!!items.length && (
					<div className={`${CLASSNAME_BOX}-show-more`}>
						{renderListGroup()}
					</div>
				)}
			</div>

			<div
				className={`${CLASSNAME_BOX}-square`}
				style={{backgroundColor: color}}
			>
				{toThousands(directAccessMetric)}
			</div>
		</div>
	);
};

export default HTMLBox;
