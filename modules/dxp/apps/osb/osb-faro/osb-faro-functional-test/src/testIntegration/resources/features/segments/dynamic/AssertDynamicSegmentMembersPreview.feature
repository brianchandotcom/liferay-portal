@spira_Segments @Segments @Dynamic @team_FARO @priority_3
Feature: Dynamic segment members preview in real time
	As a Business User, I should be able to preview dynamic segment members in real time

	Background: [Setup] Navigate to Segments page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown

	Scenario: Assert members preview updates
		Given I create a criteria with the following conditions:
			| worksFor | is | schneider and sons |
		When I click the Dynamic Segment membership preview
		And I search for "Randy Smith"
		Then I should see an individual named "Randy Smith" in the table
		When I click the "Done" button
		And I create a criteria with the following conditions:
			| gender | is | female |
		And I click the Dynamic Segment membership preview
		And I search for "Randy Smith"
		Then I should not see an individual named "Randy Smith" in the table