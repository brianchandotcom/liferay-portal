@spira_Pagination @Pagination @Segments @List @team_FARO @priority_3
Feature: Paginate static segment "Add Members" creation model
	As a Business User, I should be able to paginate to second page and view I am on second page

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button

	Scenario: Paginate to second page of "Add Members"
		Given I click the "Static Segment" dropdown option
		And I click the "Add Members" button
		When I go to page "2" in the creation modal
		Then I should be on page "2" in the creation modal
		And I should see an individual named "Ben Larkin" in the table