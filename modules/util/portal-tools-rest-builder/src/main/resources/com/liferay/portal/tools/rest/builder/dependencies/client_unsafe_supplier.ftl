package ${configYAML.apiPackagePath}.client.function;

<#assign javaEePrefix = freeMarkerTool.getJavaEePrefix(configYAML) />

import ${javaEePrefix}.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@FunctionalInterface
@Generated("")
public interface UnsafeSupplier<T, E extends Throwable> {

	public T get() throws E;

}