import React, {useState, createContext, useContext} from 'react';
import LoadingState from 'shared/components/loading/LoadingState';

function useLoading() {
	const [loading, setLoading] = useState(false);

	return {
		loading,
		setLoading
	};
}

const LoadingContext = createContext(false);

function Loading({children}) {
	const {loading} = useContext(LoadingContext);

	return (
		loading &&
		(children || (
			<div className="pb-6 pt-5">
				<LoadingState />
			</div>
		))
	);
}

export {Loading, LoadingContext, useLoading};
