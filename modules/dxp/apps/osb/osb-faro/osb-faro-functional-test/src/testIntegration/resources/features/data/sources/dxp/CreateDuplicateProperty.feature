@spira_Property @Property @team_FARO @priority_3
Feature: Create a Duplicate Property
	As an Business User, a Property with a duplicate name should have an incrementing identifier appended to it's name

	Background: [Setup] Login
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert duplicate Property is correctly named
		Given I go to the "Properties" page
		When I create a new Property named "CreateDuplicateProperty"
		And I go to the "Properties" page
		When I create a new Property named "CreateDuplicateProperty"
		And I go to the "Properties" page
		Then I should see a Property named "CreateDuplicateProperty" in the table
		And I should see a Property named "CreateDuplicateProperty (1)" in the table