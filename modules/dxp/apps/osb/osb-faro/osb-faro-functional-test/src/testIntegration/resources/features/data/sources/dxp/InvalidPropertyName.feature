@spira_Property @Property @team_FARO @priority_3
Feature: Assert Invalid Property name unable to be created
	As a Business User, I should not be able to create an invalid property name

	Background: [Setup] Go to Properties page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Properties" page

	Scenario: Create invalid property name
		When I attempt to create a new Property named "60characterlimitpropertyname12345678901234567890123456789012345678"
		Then I should not be able to click the "Save" button