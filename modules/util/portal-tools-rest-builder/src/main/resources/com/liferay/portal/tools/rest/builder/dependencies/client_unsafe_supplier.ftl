package ${configYAML.apiPackagePath}.client.function;

<#if !freeMarkerTool.isVersionCompatible(configYAML, 10) || (useJavax?string == "true")>
	import javax.annotation.Generated;

<#else>
	import jakarta.annotation.Generated;
</#if>

/**
 * @author ${configYAML.author}
 * @generated
 */
@FunctionalInterface
@Generated("")
public interface UnsafeSupplier<T, E extends Throwable> {

	public T get() throws E;

}