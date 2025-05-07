package ${configYAML.apiPackagePath}.client.function;

import ${configYAML.javaEePackage}.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@FunctionalInterface
@Generated("")
public interface UnsafeSupplier<T, E extends Throwable> {

	public T get() throws E;

}