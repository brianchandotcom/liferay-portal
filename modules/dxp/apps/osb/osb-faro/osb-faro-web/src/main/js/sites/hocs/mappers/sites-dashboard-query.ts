import {safeResultToProps} from 'shared/util/mappers';

const mapResultToProps = safeResultToProps(
	({dataSources}: {dataSources: unknown}) => ({
		sites: dataSources
	})
);

export {mapResultToProps};
