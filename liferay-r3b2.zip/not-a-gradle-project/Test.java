import com.acme.headless.r3b2.client.dto.v1_0.Foo;
import com.acme.headless.r3b2.client.resource.v1_0.FooResource;

import com.acme.headless.r3b2.client.dto.v1_0.Goo;
import com.acme.headless.r3b2.client.resource.v1_0.GooResource;



public class Test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FooResource.Builder builder = FooResource.builder();

		FooResource fooResource = builder.authentication(
			"test@liferay.com", "test"
		).build();

		Foo foo1 = fooResource.getFoo(1L);
		System.out.println(foo1);

        Foo foo2 = fooResource.getFoo(2L);
		System.out.println(foo2);

        Foo foo3 = fooResource.getFoo(3L);
		System.out.println(foo3);



		public static void main(String[] args) throws Exception {
			GooResource.Builder builder = GooResource.builder();
	
			GooResource gooResource = builder.authentication(
				"test@liferay.com", "test"
			).build();
	
			Goo goo1 = gooResource.getGoo(1L);
			System.out.println(goo1);
	
			Goo goo2 = gooResource.getGoo(2L);
			System.out.println(goo2);
	
			Goo goo3 = gooResource.getGoo(3L);
			System.out.println(goo3);

			Goo goo4 = gooResource.getGoo(4L);
			System.out.println(goo4);

			Goo goo5 = gooResource.getGoo(5L);
			System.out.println(goo5);

			Goo goo6 = gooResource.getGoo(6L);
			System.out.println(goo6);

	}

}