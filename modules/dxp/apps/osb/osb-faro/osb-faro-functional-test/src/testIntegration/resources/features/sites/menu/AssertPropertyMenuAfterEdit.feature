@spira_Properties @Property @team_FARO @priority_3
Feature: Assert the Property Menu after editing a Property
	As an Business User, I should see that the Property Menu reflects having edited a Property

	Background: [Setup] Add a Property
		* I go to the "Home" page
	  	* I login as "test@faro.io:test"
	  	* I should see the "Sites" page
	  	* I go to the "Properties" page
	  	* I create a new Property named "AssertPropertyMenuAfterEdit - ${Random.1}"

	Scenario: Assert the Property Menu after editing a Property
		Given I go to the "Properties" page
		When I edit the Property "AssertPropertyMenuAfterEdit - ${Random.1}" name to "AssertPropertyMenuAfterEdit EDITED"
		And I go to the "Sites" page
		And I click the Property Dropdown Menu
		Then I should see the following Properties in the Property Dropdown Menu:
			| AssertPropertyMenuAfterEdit EDITED |