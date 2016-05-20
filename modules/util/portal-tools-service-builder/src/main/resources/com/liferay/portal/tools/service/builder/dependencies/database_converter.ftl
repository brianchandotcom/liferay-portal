package ${packagePath}.service.convert.database;

import com.liferay.portal.convert.database.DatabaseConverter;
import com.liferay.portal.convert.database.ModelRegistry;
import com.liferay.portal.convert.util.ModelMigrator;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.model.BaseModel;

<#list entities as entity>
	import ${packagePath}.model.impl.${entity.name}Impl;
</#list>

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author ${author}
 * @generated
 */
public class ${databaseConverterName} implements DatabaseConverter {

	@Override
	public void convert(DataSource dataSource) throws Exception {
		_modelMigrator.migrate(dataSource, _getModels());
	}

	public void setModelMigrator(ModelMigrator modelMigrator) {
		_modelMigrator = modelMigrator;
	}

	private List<Class<? extends BaseModel<?>>> _getModels() {
		List<Class<? extends BaseModel<?>>> models =
			new ArrayList<Class<? extends BaseModel<?>>>();

		<#list entities as entity>
			<#if entity.hasColumns()>
			models.add(${entity.name}Impl.class);
			</#if>
		</#list>

		return models;
	}

	@BeanReference(type = ModelMigrator.class)
	private ModelMigrator _modelMigrator;

}