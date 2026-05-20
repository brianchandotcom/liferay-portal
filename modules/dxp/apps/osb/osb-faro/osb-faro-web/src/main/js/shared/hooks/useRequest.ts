import {debounce} from 'lodash/fp';
import {useCallback, useRef, useState} from 'react';
import {useDeepEqualEffect} from 'shared/hooks/useDeepEqualEffect';

export const useRequest = <TParams extends object, TData>({
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
	dataSourceFn?: (params: TParams) => Promise<TData> | undefined;
	debounceDelay?: number;
	initialState?: {data: TData | null; error: boolean; loading: boolean};
	normalize?: (params: any) => any;
	resetStateIfSkipingRequest?: boolean;
	skipRequest?: boolean;
	variables: TParams;
}) => {
	const requestAbortControllerRef = useRef<AbortController>();
	const debounceRef = useRef<ReturnType<typeof debounce>>();

	const debouncedDataSourceFn = useCallback<any>(
		debounce(debounceDelay)(vars => {
			if (!dataSourceFn) {
				return;
			}

			requestAbortControllerRef.current = new AbortController();

			const promise = dataSourceFn(vars);
			if (!promise) {
				return;
			}

			promise
				.then(result => {
					if (requestAbortControllerRef.current?.signal.aborted) {
						return;
					}

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
			requestAbortControllerRef.current?.abort();
		};
	}, [skipRequest, variables]);

	return state;
};
