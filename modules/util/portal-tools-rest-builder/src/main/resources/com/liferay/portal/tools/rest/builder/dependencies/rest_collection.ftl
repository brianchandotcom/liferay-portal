package ${configYAML.apiPackagePath}.dto;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@XmlRootElement(name = "collection")
@XmlSeeAlso(
	{
		<#list openAPIYAML.components.schemas?keys as schemaName>
			${schemaName}.class

			<#if schemaName_has_next>
				,
			</#if>
		</#list>
	}
)
public class RESTCollection<T> {

	public RESTCollection() {
		_items = Collections.emptyList();
		_totalCount = 0;
	}

	public RESTCollection(Collection<T> items, int totalCount) {
		_items = items;
		_totalCount = totalCount;
	}

	@XmlElement(name = "item")
	public Collection<T> getItems() {
		return _items;
	}

	@XmlElement
	public int getItemsCount() {
		return _items.size();
	}

	@XmlElement
	public int getTotalCount() {
		return _totalCount;
	}

	private final Collection<T> _items;
	private final int _totalCount;

}