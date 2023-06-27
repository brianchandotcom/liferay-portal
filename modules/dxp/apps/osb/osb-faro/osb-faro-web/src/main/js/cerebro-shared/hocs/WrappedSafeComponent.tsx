import Loading from 'shared/components/Loading';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';

interface IWrappedSafeComponentProps extends React.HTMLAttributes<HTMLElement> {
	children: (result: any) => React.ReactElement;
	data: any;
	error?: any;
	loading?: boolean;
	mapper?: Function;
}

/**
 * WrappedSafeComponent
 * Used with Apollo React Hooks
 */
const WrappedSafeComponent: React.FC<IWrappedSafeComponentProps> = ({
	children,
	data,
	error = false,
	loading = false,
	mapper
}) => {
	let result = {};
	let empty = false;

	try {
		if (loading) {
			return <Loading />;
		}

		if (error) {
			console.error(error); // eslint-disable-line no-console

			return (
				<NoResultsDisplay
					title={Liferay.Language.get('sorry-an-error-occurred')}
				/>
			);
		}

		({empty, ...result} = mapper ? mapper(data) : data);

		if (empty) {
			return (
				<NoResultsDisplay
					title={Liferay.Language.get('empty-message')}
				/>
			);
		}
	} catch (error) {
		console.error(error); // eslint-disable-line no-console
	}

	return children(result);
};

export default WrappedSafeComponent;
