package ${configYAML.apiPackagePath}.client.function;

import ${configYAML.javaEEPackage}.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@FunctionalInterface
@Generated("")
public interface UnsafeSupplier<T, E extends Throwable> {

	public T get() throws E;

}