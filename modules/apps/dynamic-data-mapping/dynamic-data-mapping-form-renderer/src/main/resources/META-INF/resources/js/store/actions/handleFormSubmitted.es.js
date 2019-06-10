import {evaluate} from '../../util/evaluation.es';
import {PagesVisitor} from '../../util/visitors.es';

export default evaluatorContext => {
	return evaluate(null, evaluatorContext).then(evaluatedPages => {
		let validForm = true;
		const visitor = new PagesVisitor(evaluatedPages);

		visitor.mapFields(({valid}) => {
			if (!valid) {
				validForm = false;
			}
		});

		return Promise.resolve(validForm);
	});
};
