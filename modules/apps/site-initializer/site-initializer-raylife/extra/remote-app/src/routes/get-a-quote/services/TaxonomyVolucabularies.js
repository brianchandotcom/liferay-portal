import {axios} from '../../../common/services/liferay/api';
import {Liferay} from '../../../common/utils/liferay';

export function getTaxonomyCategories(id, taxonomyCategoryName) {
	const taxonomyCategories = axios.get(
		`/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${id}/taxonomy-categories?filter=contains(name, '${taxonomyCategoryName}')`
	);

	return taxonomyCategories;
}

export function getTaxonomyVocabularies() {
	const taxonomyVocabularyName = 'Raylife';

	const taxonomyVocabularies = axios.get(
		`o/headless-admin-taxonomy/v1.0/sites/${Liferay.ThemeDisplay.getCompanyGroupId()}/taxonomy-vocabularies?filter=name eq '${taxonomyVocabularyName}'`
	);

	return taxonomyVocabularies;
}
