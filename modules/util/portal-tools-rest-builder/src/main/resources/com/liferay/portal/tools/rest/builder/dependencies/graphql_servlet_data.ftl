package ${configYAML.apiPackagePath}.internal.graphql.servlet.${escapedVersion};

import ${configYAML.apiPackagePath}.internal.graphql.mutation.${escapedVersion}.Mutation;
import ${configYAML.apiPackagePath}.internal.graphql.query.${escapedVersion}.Query;

import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "${configYAML.application.baseURI}-graphql/${escapedVersion}";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

}