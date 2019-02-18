package ${configYAML.apiPackagePath}.internal.jaxrs.message.body.${versionDirName};

<#compress>
	<#list allSchemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
		import ${configYAML.apiPackagePath}.internal.dto.${versionDirName}.${schemaName}Impl;
	</#list>
</#compress>

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.io.IOException;
import java.io.InputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(
	property = {
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=${configYAML.application.name})",
		"osgi.jaxrs.name=${configYAML.application.name}.${versionDirName}.JSONMessageBodyReader"
	},
	service = MessageBodyReader.class
)
@Consumes(MediaType.APPLICATION_JSON)
@Generated("")
@Provider
public class JSONMessageBodyReader implements MessageBodyReader<Object> {

	@Override
	public boolean isReadable(
		Class<?> clazz, Type genericType, Annotation[] annotations,
		MediaType mediaType) {

		<#list allSchemas?keys as schemaName>
			if (clazz.equals(${schemaName}.class)) {
				return true;
			}
		</#list>

		return false;
	}

	@Override
	public Object readFrom(
			Class<Object> clazz, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> multivaluedMap,
			InputStream inputStream)
		throws IOException, WebApplicationException {

		return _objectMapper.readValue(inputStream, clazz);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			SimpleModule simpleModule =
				new SimpleModule("${configYAML.application.name}",
					Version.unknownVersion());

			SimpleAbstractTypeResolver simpleAbstractTypeResolver =
				new SimpleAbstractTypeResolver();

			<#list allSchemas?keys as schemaName>
			simpleAbstractTypeResolver.addMapping(
				${schemaName}.class, ${schemaName}Impl.class);
			</#list>

			simpleModule.setAbstractTypes(simpleAbstractTypeResolver);

			registerModule(simpleModule);

			setDateFormat(new ISO8601DateFormat());
		}
	};

}