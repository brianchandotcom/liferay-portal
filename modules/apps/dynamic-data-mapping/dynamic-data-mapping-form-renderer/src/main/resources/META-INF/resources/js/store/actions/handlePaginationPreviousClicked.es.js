import {evaluate} from '../../util/evaluation.es';

export default (evaluatorContext, dispatch) => {
	const {activePage} = evaluatorContext;

	return evaluate(null, evaluatorContext).then(evaluatedPages => {
		let previousActivePageIndex = activePage;

		for (let i = activePage - 1; i > -1; i--) {
			if (evaluatedPages[i].enabled) {
				previousActivePageIndex = i;

				break;
			}
		}

		dispatch('activePageUpdated', Math.max(previousActivePageIndex, 0));
	});
};
