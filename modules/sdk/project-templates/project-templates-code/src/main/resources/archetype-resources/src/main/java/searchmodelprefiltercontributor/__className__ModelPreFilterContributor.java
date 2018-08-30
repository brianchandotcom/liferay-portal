package ${package}.${template};

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${author}
 */
@Component(
	immediate = true, property = "indexer.class.name=$className.toUpperCase()",
	service = com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor.class
)
public class ${className}ModelPreFilterContributor
	implements com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		RangeTermFilter rangeTermFilter = new RangeTermFilter(
			Field.CREATE_DATE, true, true, "now-1h", null);

		booleanFilter.add(rangeTermFilter, BooleanClauseOccur.MUST);
	}

}