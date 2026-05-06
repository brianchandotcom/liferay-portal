import {useEffect, useState} from 'react';
import {useFDSBundle} from './useFDSBundle';

interface IFDSState {
	filters: any[];
	search: string;
}

const EMPTY_FDS_STATE: IFDSState = {filters: [], search: ''};

export const useFDSState = (fdsId: string): IFDSState => {
	const bundle = useFDSBundle();
	const [state, setState] = useState<IFDSState>(EMPTY_FDS_STATE);

	useEffect(() => {
		if (!bundle) {
			return;
		}

		const State = (window as any).Liferay?.State;

		if (!State) {
			return;
		}

		const atom = bundle.getFDSAtom({fdsName: fdsId});

		const apply = (rawState: any) => {
			setState({
				filters: rawState?.filters ?? [],
				search: rawState?.search?.query ?? ''
			});
		};

		apply(State.read(atom));

		const {dispose} = State.subscribe(atom, apply);

		return dispose;
	}, [bundle, fdsId]);

	return state;
};
