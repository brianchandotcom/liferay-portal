import {Filters} from 'shared/util/filter';
import {getVariables, safeResultToProps} from 'shared/util/mappers';
import {RangeSelectors} from 'shared/types';

type AssetPage = {
	assetId: string;
	assetTitle?: string;
};

type TouchpointListResult = {
	assetPages?: AssetPage[];
};

const mapResultToProps = safeResultToProps(
	({assetPages}: TouchpointListResult) => {
		const items =
			assetPages &&
			assetPages.map(({assetId, assetTitle}: AssetPage) => ({
				title: assetTitle ? assetTitle : assetId,
				touchpoint: assetId
			}));

		return {
			items
		};
	}
);

interface IMapPropsToOptionsArgs {
	assetType: string;
	filters: Filters;
	rangeSelectors: RangeSelectors;
	router: {params: Record<string, string | undefined>};
}

/**
 * Map Props to Options
 * @param {object} param0 props
 * @param {object} param1 context
 */
const mapPropsToOptions = ({
	assetType,
	filters,
	rangeSelectors,
	router: {params}
}: IMapPropsToOptionsArgs) => {
	const {variables} = getVariables({filters, params, rangeSelectors});

	return {
		variables: {
			...variables,
			assetType: assetType.toUpperCase()
		}
	};
};

export {mapPropsToOptions, mapResultToProps};
