import Promise from 'metal-promise';
import useDeepEqualEffect from './useDeepEqualEffect';
import {debounce} from 'lodash/fp';
import {useCallback, useRef, useState} from 'react';

const useRequest = ({
	dataSourceFn,
	debounceDelay = 0,
	initialState = {
		data: null,
		error: false,
		loading: true
	},
	normalize = val => val,
	resetStateIfSkipingRequest = false,
	skipRequest = false,
	variables
}: {
	dataSourceFn: (params: {[key: string]: any}) => Promise<any>;
	debounceDelay?: number;
	initialState?: {
		data: any;
		error: boolean;
		loading: boolean;
	};
	normalize?: (params: any) => any;
	resetStateIfSkipingRequest?: boolean;
	skipRequest?: boolean;
	variables: {[key: string]: any};
}) => {
	const requestRef = useRef<typeof Promise>();
	const debounceRef = useRef<ReturnType<typeof debounce>>();

	const debouncedDataSourceFn = useCallback<any>(
		debounce(debounceDelay)(vars => {
			requestRef.current = dataSourceFn(vars)
				.then(result => {
					setState({
						...state,
						data: normalize(result),
						loading: false
					});
				})
				.catch(
					err =>
						!err.IS_CANCELLATION_ERROR &&
						setState({...state, error: true, loading: false})
				);
		}),
		[]
	);

	const getData = () => {
		setState({...state, loading: true});

		debounceRef.current = debouncedDataSourceFn(variables);
	};

	const [state, setState] = useState({
		...initialState,
		refetch: getData
	});

	useDeepEqualEffect(() => {
		if (!skipRequest) {
			getData();
		} else if (resetStateIfSkipingRequest) {
			setState({...state, ...initialState});
		}

		return () => {
			debounceRef.current?.cancel?.();
			requestRef.current?.cancel?.();
		};
	}, [skipRequest, variables]);

	return state;
};

export default useRequest;
