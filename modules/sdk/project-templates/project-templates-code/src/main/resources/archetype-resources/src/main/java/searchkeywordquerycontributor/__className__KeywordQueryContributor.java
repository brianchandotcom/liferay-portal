package ${package}.${template};

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author ${author}
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.calendar.model.Calendar",
	service = KeywordQueryContributor.class
)
public class ${className}KeywordQueryContributor implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		queryHelper.addSearchLocalizedTerm(
			booleanQuery, searchContext, Field.CAPTION, true);
	}

	@Reference
	protected QueryHelper queryHelper;

}