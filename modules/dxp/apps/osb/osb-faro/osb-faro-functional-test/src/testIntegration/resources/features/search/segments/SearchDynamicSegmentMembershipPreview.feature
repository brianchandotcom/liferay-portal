@spira_Search @Search @Segments @List @team_FARO @priority_3
Feature: Search a Dynamic Segment's Membership Preview Modal
	As a Business User, I should be able to search a Dynamic Individuals Segment's membership preview modal

	Background: [Setup] Begin creating a Dynamic Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown

	Scenario: Search the Dynamic Individuals Segment's Membership Preview
		Given I create a criteria with the following conditions:
			| state | is | colorado |
		And I click the Dynamic Segment membership preview
		And I should see an individual named "Jeraldine Jaskolski" in the table
		When I search for "Russel Smith"
		Then I should see an individual named "Russel Smith" in the table
		And I should not see an individual named "Jeraldine Jaskolski" in the table