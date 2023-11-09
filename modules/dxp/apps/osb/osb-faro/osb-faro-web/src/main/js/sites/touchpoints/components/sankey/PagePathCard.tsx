import Card from 'shared/components/Card';
import ErrorDisplay from 'shared/components/ErrorDisplay';
import PagePathQuery from 'shared/queries/PagePathQuery';
import React from 'react';
import Sankey from './Sankey';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import {getSafeRangeSelectors} from 'shared/util/util';
import {MAIN_NODE_HEIGHT, SECONDARY_NODE_COLOR} from './utils';
import {Type} from './types';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';
import {v4 as uuidv4} from 'uuid';

type pagePathNode = {
	views: number;
	canonicalUrl: string;
	title: TitleKey;
	followingPagePathNodes: pagePathNode[];
	previousPagePathNodes: pagePathNode[];
};

enum TitleKey {
	Direct = 'direct',
	DropOffs = 'drop-offs',
	Others = 'others'
}

function getTitle(key: TitleKey, type: Type) {
	const langs = {
		[TitleKey.Direct]: Liferay.Language.get('direct-traffic'),
		[TitleKey.DropOffs]: Liferay.Language.get('drop-offs'),
		[TitleKey.Others]:
			type === Type.Previous
				? Liferay.Language.get('other-referrals')
				: Liferay.Language.get('other-pages')
	};

	return langs[key] || key;
}

function getColor(key: TitleKey) {
	if (
		key === TitleKey.Direct ||
		key === TitleKey.DropOffs ||
		key === TitleKey.Others
	) {
		return SECONDARY_NODE_COLOR;
	}

	return null;
}

function formatData({pagePath}: {pagePath: pagePathNode}) {
	const calculateTotalViews = (nodes: pagePathNode[]) =>
		nodes?.reduce((acc, {views}) => acc + views, 0) || 0;

	const formatNodes = (
		nodes: pagePathNode[],
		type: Type,
		totalViews: number
	) =>
		nodes?.map(({canonicalUrl, title, views}) => ({
			color: getColor(title),
			height: (views / totalViews) * MAIN_NODE_HEIGHT,
			id: uuidv4(),
			name: getTitle(title, type),
			type,
			url: canonicalUrl,
			views
		})) || [];

	const totalViewsPreviousPage = calculateTotalViews(
		pagePath.previousPagePathNodes
	);
	const totalViewsNextPage = calculateTotalViews(
		pagePath.followingPagePathNodes
	);

	const mainNode = {
		height: MAIN_NODE_HEIGHT,
		id: uuidv4(),
		main: true,
		name: pagePath.title,
		url: pagePath.canonicalUrl
	};

	const previousNodes = formatNodes(
		pagePath.previousPagePathNodes,
		Type.Previous,
		totalViewsPreviousPage
	);

	const followingNodes = formatNodes(
		pagePath.followingPagePathNodes,
		Type.Following,
		totalViewsNextPage
	);

	const links = [...previousNodes, ...followingNodes].map((link, index) => ({
		source: link.type === Type.Previous ? index + 1 : 0,
		target: link.type === Type.Previous ? 0 : index + 1,
		value: link.views
	}));

	const nodes = [mainNode, ...previousNodes, ...followingNodes];

	return {
		links,
		nodes
	};
}

const PagePathCard = ({rangeSelectors}) => {
	const {channelId, title, touchpoint} = useParams();
	const {data, error, loading} = useQuery(PagePathQuery, {
		variables: {
			canonicalUrl: decodeURIComponent(touchpoint),
			channelId,
			title,
			...getSafeRangeSelectors(rangeSelectors)
		}
	});

	return (
		<Card minHeight={600}>
			<Card.Header>
				<Card.Title>{Liferay.Language.get('path-analysis')}</Card.Title>
			</Card.Header>
			<Card.Body className='d-flex align-items-center justify-content-center'>
				<StatesRenderer empty={!data} error={!!error} loading={loading}>
					<StatesRenderer.Loading />

					<StatesRenderer.Error apolloError={error}>
						<ErrorDisplay />
					</StatesRenderer.Error>

					{!!data && (
						<StatesRenderer.Success>
							<Sankey data={formatData(data)} />
						</StatesRenderer.Success>
					)}
				</StatesRenderer>
			</Card.Body>
		</Card>
	);
};

export default PagePathCard;
